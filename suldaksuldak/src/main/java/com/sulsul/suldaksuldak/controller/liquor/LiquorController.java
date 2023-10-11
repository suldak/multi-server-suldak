package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            statsService.countSearchCnt(userPriKey, liquorPriKey);
            statsService.createLiquorSearchLog(liquorPriKey);
        }
        return ApiDataResponse.of(
                liquorDataService.getLiquorTotalData(liquorPriKey)
        );
    }

    @ApiOperation(
            value = "기간 별 인기 술 목록 조회",
            notes = "기간 별로 많이 검색되었던 술 목록을 조회합니다. (Pageable)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10"),
            @ApiImplicitParam(name = "startAt", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", required = true, dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "endAt", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", required = true, dataTypeClass = String.class, example = "2023-10-06T00:00:00", defaultValue = "LocalDateTime.now()")
    })
    @GetMapping(value = "/liquor-date-range")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByDateRange(
            Integer pageNum,
            Integer recordSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime startAt,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime endAt
    ) {
        Page<Long> liquorPriKeyList =
                statsService.getLiquorDataByLogStats(
                        startAt,
                        endAt == null ? LocalDateTime.now() : endAt,
                        UtilTool.getPageable(pageNum, recordSize)
                );
        if (liquorPriKeyList.getContent().isEmpty()) {
            return ApiDataResponse.of(
                    new PageImpl<>(
                            List.of(),
                            liquorPriKeyList.getPageable(),
                            liquorPriKeyList.getTotalElements()
                    )
            );
        }
        List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
        for (Long liquorPriKey: liquorPriKeyList.getContent()) {
            liquorTotalRes.add(liquorDataService.getLiquorTotalData(liquorPriKey));
        }
        return ApiDataResponse.of(
                new PageImpl<>(
                        liquorTotalRes,
                        liquorPriKeyList.getPageable(),
                        liquorPriKeyList.getTotalElements()
                )
        );
    }

//    @ApiOperation(
//            value = "유저 별 추천 술 목록 반환",
//            notes = "유저의 집게 테이블을 기준으로 유저가 많이 검색한 술의 태그로 술을 검색하여 보여줍니다. (유저 인증 Token 필요)"
//    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
//            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
//    })
//    @GetMapping(value = "/liquor-user")
//    public ApiDataResponse<List<LiquorTotalRes>> getLiquorListFromUser(
//            HttpServletRequest request,
//            Integer pageNum,
//            Integer recordSize
//    ) {
//        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
//        if (userPriKey == null) {
//            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)).getContent());
//        }
//        List<Long> liquorPriKeyList =
//                statsService.getLiquorPriKeyByUserStats(userPriKey, 5);
//
//        if (liquorPriKeyList.isEmpty()) {
//            return ApiDataResponse.of(liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize)).getContent());
//        }
//
//        return ApiDataResponse.of(
//                liquorViewService.getLiquorListByLiquorPriKey(liquorPriKeyList)
//        );
//    }
}
