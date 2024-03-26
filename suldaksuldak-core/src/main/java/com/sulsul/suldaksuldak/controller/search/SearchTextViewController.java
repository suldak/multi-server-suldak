package com.sulsul.suldaksuldak.controller.search;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.search.RecommendSearchTextRes;
import com.sulsul.suldaksuldak.dto.search.SearchTextRankingRes;
import com.sulsul.suldaksuldak.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/search/text/view")
@Api(tags = "[COMMON] 공통 검색어 조회")
public class SearchTextViewController {
    private final SearchService searchService;

    @ApiOperation(
            value = "추천 검색어 목록 조회",
            notes = "관리자가 설정해둔 추천 검색어 목록을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isActive", value = "활성화 여부", dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "limitNum", value = "검색 항목 개수 (기본 10개)", dataTypeClass = Integer.class)
    })
    @GetMapping(value = "/recommend")
    public ApiDataResponse<List<RecommendSearchTextRes>> getRecommendSearchTextList(
            Boolean isActive,
            Integer limitNum
    ) {
        return ApiDataResponse.of(
                searchService.getRecommendSearchTextList(
                                isActive,
                                limitNum == null ? 10 : limitNum
                        )
                        .stream()
                        .map(RecommendSearchTextRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "검색어 랭킹 조회",
            notes = """
                    검색어의 랭킹 목록을 조회합니다.
                    \nEx: searchHour에 11을 입력하면 9시 ~ 10시의 순위와 10시 부터 11시의 순위를 비교합니다. 
                    """
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchHour", value = "조회할 시간 (0 ~ 23)", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limitNum", value = "검색 항목 개수 (기본 10개)", dataTypeClass = Integer.class)
    })
    @GetMapping(value = "/ranking")
    public ApiDataResponse<List<SearchTextRankingRes>> getSearchTextRankingList(
            Integer searchHour,
            Integer limitNum
    ) {
        return ApiDataResponse.of(
                searchService.getSearchTextRankingList(
                            searchHour,
                            limitNum == null ? 10 : limitNum
                )
        );
    }
}
