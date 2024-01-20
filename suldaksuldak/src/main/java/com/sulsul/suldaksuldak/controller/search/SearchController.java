package com.sulsul.suldaksuldak.controller.search;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/search")
@Api(tags = "[MAIN] 검색 키워드 검색")
public class SearchController {
    private final SearchService searchService;

    @ApiOperation(
            value = "검색어 조회합니다.",
            notes = "기간별, 유저별로 검색한 검색어를 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchStartTime", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", required = true, dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "searchEndTime", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", required = true, dataTypeClass = String.class, example = "2023-10-06T00:00:00"),
            @ApiImplicitParam(name = "searchUserPriKey", value = "검색어를 조회할 유저 기본키", dataTypeClass = Long.class)
    })
    @GetMapping("/search-text")
    public ApiDataResponse<List<SearchTextDto>> getSearchText(
            HttpServletRequest request,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                    LocalDateTime searchStartTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                    LocalDateTime searchEndTime,
            Long searchUserPriKey
    ) {
        return ApiDataResponse.of(
                searchService.getSearchList(
                        searchStartTime,
                        searchEndTime,
                        searchUserPriKey
                )
        );
    }
}
