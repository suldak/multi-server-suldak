package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorRes;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/liquor/view")
@Api(tags = "[COMMON] 술 관련 정보 조회")
public class LiquorViewController {
    private final LiquorViewService liquorViewService;
    private final StatsService statsService;

    @ApiOperation(
            value = "술에 관한 모든 정보 조회 (집계)",
            notes = "술에 관한 모든 태그와 정보를 조회합니다. 유저가 해당 술을 검색했다고 판단하여 집계합니다. (인증 Token을 꼭 넣어주세요.)"
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
        }
        return ApiDataResponse.of(
                liquorViewService.getLiquorTotalData(liquorPriKey)
        );
    }

    @ApiOperation(
            value = "태그 기준 술 조회",
            notes = "태그에 해당되는 술 목록을 조회합니다. (Body가 복잡해서 POST로 수정)"
    )
    @PostMapping(value = "/liquor")
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
        Pageable pageable =
                PageRequest.of(
                        pageNum == null || pageNum < 0 ? 0 : pageNum,
                        recordSize == null || recordSize < 0 ? 10 : recordSize
                );

        return ApiDataResponse.of(
                liquorViewService.getLatestLiquor(pageable)
        );
    }
}
