package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
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
import org.springframework.web.bind.annotation.*;

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
            value = "유저의 술 즐겨찾기 추가 및 삭제 (App 개발자 분들 피드백 부탁드립니다...)",
            notes = """
                    유저가 술에 대해서 즐겨찾기를 등록하거나, 이미 즐겨찾기 상태이면 삭제합니다.\n
                    (이 API를 등록 따로 삭제 따로 만드는 것이 좋은지, 이렇게 하나의 API로 하는게 좋은지 피드백 부탁드립니다.)
                    """
    )
    @PostMapping("/like/{liquorPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> userLiquorLike(
            HttpServletRequest request,
            @PathVariable Long liquorPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                statsService.createOrDeleteLiquorLike(
                        userPriKey,
                        liquorPriKey
                )
        );
    }

    @ApiOperation(
            value = "술에 관한 모든 정보 조회 (집계)",
            notes = "술에 관한 모든 태그와 정보를 조회합니다. 유저가 해당 술을 검색했다고 판단하여 집계합니다. (유저 인증 Token 필요)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liquorPriKey", value = "술의 기본키", required = true, dataTypeClass = Long.class)
    })
    @GetMapping(value = "/{liquorPriKey:[0-9]+}")
    public ApiDataResponse<LiquorTotalRes> getLiquorTotalData (
            HttpServletRequest request,
            @PathVariable Long liquorPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey != null) {
            try {
                Boolean searchResult = statsService.countSearchCnt(userPriKey, liquorPriKey);
                if (searchResult) {
                    statsService.countSearchTagCnt(userPriKey, liquorPriKey);
                    statsService.createLiquorSearchLog(liquorPriKey);
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        return ApiDataResponse.of(
                liquorDataService.getLiquorTotalData(
                        liquorPriKey,
                        userPriKey
                )
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
            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize), null));
        }
        return getLiquorListByLiquorData(pageNum, recordSize, userPriKey);
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
            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize), null));
        }
//        LiquorTagSearchDto liquorTagSearchDto =
//                statsService.getTagListByUserPriKey(
//                        userPriKey,
//                        5,
//                        pageNum,
//                        recordSize
//                );
//        return ApiDataResponse.of(
//                liquorViewService.getLiquorByTag(
//                        userPriKey,
//                        liquorTagSearchDto,
//                        UtilTool.getPageable(pageNum, recordSize)
//                )
//        );
        return getLiquorListByTagData(pageNum, recordSize, userPriKey);
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
                    liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize), null)
            );
        }

        if (searchType.equals("TAG")) {
//            LiquorTagSearchDto liquorTagSearchDto =
//                    statsService.getTagListByUserPriKey(
//                            userPriKey,
//                            5,
//                            pageNum,
//                            recordSize
//                    );
//            return ApiDataResponse.of(
//                    liquorViewService.getLiquorByTag(
//                            userPriKey,
//                            liquorTagSearchDto,
//                            UtilTool.getPageable(pageNum, recordSize)
//                    )
//            );
            return getLiquorListByTagData(pageNum, recordSize, userPriKey);
        } else if (searchType.equals("LIQUOR")) {
            return getLiquorListByLiquorData(pageNum, recordSize, userPriKey);
        } else {
            throw new GeneralException(ErrorCode.BAD_REQUEST, "TAG / LIQUOR 중 입력해주세요.");
        }
    }

    @ApiOperation(
            value = "유저 별 즐겨찾기 된 술 조회",
            notes = "유저 별 즐겨찾기가 등록된 술 목록을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    @GetMapping("/user-like")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByLike(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        Page<LiquorLikeDto> liquorLikeDtos = liquorDataService.getLiquorLikeList(
                null,
                userPriKey,
                null,
                null,
                UtilTool.getPageable(pageNum, recordSize)
        );
        return ApiDataResponse.of(
                liquorViewService.getLiquorListByLike(
                        liquorLikeDtos,
                        userPriKey
                )
        );
    }

    /**
     * 사용자가 술을 검색한 로그를 바탕으로 술 추천
     */
    private ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByLiquorData(
            Integer pageNum,
            Integer recordSize,
            Long userPriKey
    ) {
        List<UserLiquorTagDto> liquorPriKeyList =
                statsService.getLiquorPriKeyByUserStats(userPriKey, 5);

        if (liquorPriKeyList.isEmpty()) {
            return ApiDataResponse.of(
                    liquorViewService.getLatestLiquor(
                            UtilTool.getPageable(pageNum, recordSize),
                            userPriKey
                    ));
        }

        return ApiDataResponse.of(
                liquorViewService.getLiquorListByLiquor(
                        liquorPriKeyList,
                        UtilTool.getPageable(
                                pageNum,
                                recordSize
                        ),
                        userPriKey
                )
        );
    }

    /**
     * 사용자가 술의 태그를 검색한 로그를 바탕으로 술 추천
     */
    private ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByTagData(
            Integer pageNum,
            Integer recordSize,
            Long userPriKey
    ) {
        List<UserLiquorTagDto> liquorPriKeyList =
                statsService.getLiquorPriKeyByUserStats(userPriKey, 5);

        if (liquorPriKeyList.isEmpty()) {
            return ApiDataResponse.of(
                    liquorViewService.getLatestLiquor(
                            UtilTool.getPageable(pageNum, recordSize),
                            userPriKey
                    ));
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
                        userPriKey,
                        liquorTagSearchDto,
                        UtilTool.getPageable(pageNum, recordSize)
                )
        );
    }
}
