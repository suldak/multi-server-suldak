package com.sulsul.suldaksuldak.controller.liquor;


import com.sulsul.suldaksuldak.Service.liquor.LiquorTagService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/tag")
@Api(tags = "[ADMIN] 술 태그 관리")
public class LiquorTagController {
    private final LiquorTagService liquorTagService;

    @ApiOperation(
            value = "술과 추천 안주 연결",
            notes = "술과 추천 안주를 연결합니다."
    )
    @PostMapping(value = "/liquor-snack")
    public ApiDataResponse<Boolean> createLiquorToSnack(
            Long liquorPriKey,
            Long liquorSnackPriKey
    ) {
        return ApiDataResponse.of(
                liquorTagService.createLiquorToSnack(
                        liquorPriKey,
                        liquorSnackPriKey
                )
        );
    }

    @ApiOperation(
            value = "술과 판매처 안주 연결",
            notes = "술과 판매처를 연결합니다."
    )
    @PostMapping(value = "/liquor-sell")
    public ApiDataResponse<Boolean> createLiquorToSell(
            Long liquorPriKey,
            Long liquorSellPriKey
    ) {
        return ApiDataResponse.of(
                liquorTagService.createLiquorToSell(
                        liquorPriKey,
                        liquorSellPriKey
                )
        );
    }
}
