package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.common.LiquorDataService;
import com.sulsul.suldaksuldak.service.common.LiquorViewService;
import com.sulsul.suldaksuldak.service.stats.StatsService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/liquor")
@Api(tags = "[MAIN] 유저 기준 술 정보 조회 및 관리")
public class LiquorController {
    private final LiquorViewService liquorViewService;
    private final LiquorDataService liquorDataService;
    private final StatsService statsService;

    @ApiOperation(
            value = "술에 관한 모든 정보 조회 (집계)",
            notes = "술에 관한 모든 태그와 정보를 조회합니다. 유저가 해당 술을 검색했다고 판단하여 집계합니다. (유저 인증 Token 필요)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liquorPriKey", value = "술의 기본키", required = true, dataTypeClass = Long.class)
    })
    @GetMapping(value = "/liquor")
    public ApiDataResponse<LiquorTotalRes> getLiquorTotalData (
            HttpServletRequest request,
            Long liquorPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey != null) {
            statsService.countSearchTagCnt(userPriKey, liquorPriKey);
            statsService.countSearchCnt(userPriKey, liquorPriKey);
            statsService.createLiquorSearchLog(liquorPriKey);
        }
        return ApiDataResponse.of(
                liquorDataService.getLiquorTotalData(liquorPriKey)
        );
    }

    @ApiOperation(
            value = "유저 별 추천 술 목록 반환 (술 기준)",
            notes = "유저의 술 집계 테이블을 기준으로 유저가 많이 검색한 술의 태그로 술을 검색하여 보여줍니다. (유저 인증 Token 필요)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    @GetMapping(value = "/user-liquor")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListFromLiquor(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null) {
            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)));
        }
        List<UserLiquorTagDto> liquorPriKeyList =
                statsService.getLiquorPriKeyByUserStats(userPriKey, 5);

        if (liquorPriKeyList.isEmpty()) {
            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)));
        }

        return ApiDataResponse.of(
                liquorViewService.getLiquorListByLiquor(
                        liquorPriKeyList,
                        UtilTool.getPageable(
                                pageNum,
                                recordSize
                        )
                )
        );
    }

    @ApiOperation(
            value = "유저 별 추천 술 목록 반환 (태그 기준)",
            notes = "유저의 태그 집계 테이블을 기준으로 유저가 많이 검색한 술의 태그로 술을 검색하여 보여줍니다. (유저 인증 Token 필요)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    @GetMapping(value = "/user-tag")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListFromTag(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null) {
            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)));
        }
        LiquorTagSearchDto liquorTagSearchDto =
                statsService.getTagListByUserPriKey(
                        userPriKey,
                        5,
                        pageNum,
                        recordSize
                );
        return ApiDataResponse.of(
                liquorViewService.getLiquorByTag(
                        liquorTagSearchDto,
                        UtilTool.getPageable(pageNum, recordSize)
                )
        );
    }

    @ApiOperation(
            value = "유저 별 추천 술 목록 반환 (통합 API)",
            notes = "유저 별 추천 술을 GET 파라미터에 따라서 술 기준과 태그 기준으로 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10"),
            @ApiImplicitParam(name = "searchType", value = "검색 기준 (TAG / LIQUOR)", required = true, dataTypeClass = String.class, defaultValue = "TAG")
    })
    @GetMapping("/user")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByUser(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize,
            String searchType
    ) {
        if (searchType == null || searchType.isBlank())
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "searchType을 입력해주세요."
            );
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null) {
            return ApiDataResponse.of(
                    liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize))
            );
        }

        if (searchType.equals("TAG")) {
            LiquorTagSearchDto liquorTagSearchDto =
                    statsService.getTagListByUserPriKey(
                            userPriKey,
                            5,
                            pageNum,
                            recordSize
                    );
            return ApiDataResponse.of(
                    liquorViewService.getLiquorByTag(
                            liquorTagSearchDto,
                            UtilTool.getPageable(pageNum, recordSize)
                    )
            );
        } else if (searchType.equals("LIQUOR")) {
            List<UserLiquorTagDto> liquorPriKeyList =
                    statsService.getLiquorPriKeyByUserStats(userPriKey, 5);

            if (liquorPriKeyList.isEmpty()) {
                return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)));
            }

            return ApiDataResponse.of(
                    liquorViewService.getLiquorListByLiquor(
                            liquorPriKeyList,
                            UtilTool.getPageable(
                                    pageNum,
                                    recordSize
                            )
                    )
            );
        } else {
            throw new GeneralException(ErrorCode.BAD_REQUEST, "TAG / LIQUOR 중 입력해주세요.");
        }
    }

}
