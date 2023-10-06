package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorRes;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.service.common.LiquorViewService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/liquor/view")
@Api(tags = "[COMMON] 술 관련 정보 조회")
public class LiquorViewController {
    private final LiquorViewService liquorViewService;

    @ApiOperation(
            value = "태그 및 검색 키워드로 술 조회",
            notes = "태그와 검색 키워드에 해당되는 술 목록을 조회합니다. (Body가 복잡해서 POST로 수정)"
    )
    @PostMapping(value = "/liquor-search")
    public ApiDataResponse<List<LiquorRes>> getLiquorByTags(
            @RequestBody LiquorTotalReq liquorTotalReq
    ) {
        return ApiDataResponse.of(
                liquorViewService.getLiquorByTag(liquorTotalReq)
                        .stream()
                        .map(LiquorRes::from)
                        .collect(Collectors.toList())
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
            Integer pageNum,
            Integer recordSize
    ) {
        return ApiDataResponse.of(
                liquorViewService.getLatestLiquor(UtilTool.getPageable(pageNum, recordSize))
        );
    }
}
