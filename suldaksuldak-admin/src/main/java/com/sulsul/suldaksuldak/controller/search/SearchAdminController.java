package com.sulsul.suldaksuldak.controller.search;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.search.RecommendSearchTextReq;
import com.sulsul.suldaksuldak.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/search/text")
@Api(tags = "[ADMIN] 검색어 관리")
public class SearchAdminController {
    private final SearchService searchService;

    @ApiOperation(
            value = "추천 검색어 생성 및 수정",
            notes = "추천 검색어를 생성하거나 수정합니다."
    )
    @PostMapping("/recommend")
    public ApiDataResponse<Boolean> createRecommendSearchText(
            @RequestBody RecommendSearchTextReq recommendSearchTextReq
    ) {
        return ApiDataResponse.of(
                searchService.createRecommendSearchText(
                        recommendSearchTextReq.toDto()
                )
        );
    }

    @ApiOperation(
            value = "추천 검색어 활성화 여부 수정",
            notes = "추천 검색어의 활성화 여부를 변경합니다. (기존 상태와 반대로 설정됨)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "추천 검색어 기본키", dataTypeClass = Long.class)
    })
    @PutMapping("/recommend/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> updateRecommendSearchText(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                searchService.updateRecommendSearchTextActive(
                        priKey
                )
        );
    }

    @ApiOperation(
            value = "추천 검색어 삭제",
            notes = "추천 검색어를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "추천 검색어 기본키", dataTypeClass = Long.class)
    })
    @DeleteMapping("/recommend/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deleteRecommendSearchText(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                searchService.deleteRecommendSearchText(priKey)
        );
    }
}
