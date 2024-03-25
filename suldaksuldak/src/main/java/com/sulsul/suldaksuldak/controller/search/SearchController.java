package com.sulsul.suldaksuldak.controller.search;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.search.SearchService;
import com.sulsul.suldaksuldak.tool.UtilTool;
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
@Api(tags = "[MAIN] 검색어 조회")
public class SearchController {
    private final SearchService searchService;

    @ApiOperation(
            value = "검색어 조회",
            notes = "기간별, 유저가 검색한 검색어를 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchStartTime", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "searchEndTime", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00"),
            @ApiImplicitParam(name = "limitNum", value = "검색 항목 개수 (기본 10개)", dataTypeClass = Integer.class)
    })
    @GetMapping("/search-text")
    public ApiDataResponse<List<SearchTextDto>> getSearchText(
            HttpServletRequest request,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                    LocalDateTime searchStartTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                    LocalDateTime searchEndTime,
            Integer limitNum
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "사용자 정보를 찾을 수 없습니다."
            );
        return ApiDataResponse.of(
                searchService.getSearchList(
                        searchStartTime,
                        searchEndTime,
                        userPriKey,
                        limitNum == null ? 10 : limitNum
                )
        );
    }
}
