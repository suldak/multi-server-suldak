package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchReq;
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
@RequestMapping("/api/liquor/view")
@Api(tags = "[COMMON] 술 관련 정보 조회")
public class LiquorViewController {
    private final LiquorViewService liquorViewService;
    private final StatsService statsService;
    private final LiquorDataService liquorDataService;

    @ApiOperation(
            value = "태그 및 검색 키워드로 술 조회",
            notes = "태그와 검색 키워드에 해당되는 술 목록을 조회합니다."
    )
//    @PostMapping(value = "/liquor-search")
    @GetMapping(value = "/liquor-search")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorByTags(
//            LiquorTagSearchDto liquorTagSearchDto
            HttpServletRequest request,
            LiquorTagSearchReq liquorTagSearchReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);

        return ApiDataResponse.of(
                liquorViewService.getLiquorByTag(
                        userPriKey,
                        liquorTagSearchReq.toDto(),
                        UtilTool.getPageable(
                                liquorTagSearchReq.getPageNum(),
                                liquorTagSearchReq.getRecordSize()
                        )
                )
        );
    }

    @ApiOperation(
            value = "최신 순으로 술 정렬",
            notes = "DB 생성일자 기준으로 최신에 등록된 술 순서대로 조회합니다. (Pageable)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    @GetMapping(value = "/liquor-latest")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListLatest(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        return ApiDataResponse.of(
                liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize), userPriKey)
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
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime startAt,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime endAt
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
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
            liquorTotalRes.add(liquorDataService.getLiquorTotalData(liquorPriKey, userPriKey));
        }
        return ApiDataResponse.of(
                new PageImpl<>(
                        liquorTotalRes,
                        liquorPriKeyList.getPageable(),
                        liquorPriKeyList.getTotalElements()
                )
        );
    }

    @ApiOperation(
            value = "즐겨찾기가 많은 순으로 술 목록 조회",
            notes = "유저가 즐겨찾기를 많이 등록한 술 순서대로 목록을 조회합니다. (Pageable)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10"),
            @ApiImplicitParam(name = "startAt", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "endAt", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00", defaultValue = "LocalDateTime.now()")
    })
    @GetMapping("/liquor-like")
    public ApiDataResponse<Page<LiquorTotalRes>> getLiquorListByLike(
            HttpServletRequest request,
            Integer pageNum,
            Integer recordSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime startAt,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime endAt
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        Page<LiquorLikeDto> liquorLikeDtos = liquorDataService.getLiquorLikeList(
                null,
                null,
                startAt,
                endAt,
                UtilTool.getPageable(
                        pageNum,
                        recordSize
                )
        );
        return ApiDataResponse.of(
                liquorViewService.getLiquorListByLike(
                        liquorLikeDtos,
                        userPriKey
                )
        );
    }
}
