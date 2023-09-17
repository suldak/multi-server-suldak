package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeReq;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.LiquorSellDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.common.LiquorViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/liquor/view")
@Api(tags = "[COMMON] 술 관련 정보 조회")
public class LiquorViewController {
    private final LiquorViewService liquorViewService;

    @ApiOperation(
            value = "레시피 조회",
            notes = "술에 해당하는 레시피를 조회합니다."
    )
    @GetMapping(value = "/liquor-recipe")
    public ApiDataResponse<LiquorRecipeRes> getLiquorRecipe(
            @RequestBody(required = true, description = "술 기본키") Long liquorPriKey
    ) {
        Optional<LiquorRecipeDto> liquorRecipeDto = liquorViewService.getLiquorRecipe(liquorPriKey);
        if (liquorRecipeDto.isEmpty()) {
            throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND RECIPE DATA");
        } else {
            return ApiDataResponse.of(LiquorRecipeRes.from(liquorRecipeDto.get()));
        }
    }

    @ApiOperation(
            value = "추천 안주 조회",
            notes = "술에 해당하는 추천 안주를 조회합니다."
    )
    @GetMapping(value = "/liquor-snack")
    public ApiDataResponse<List<LiquorSnackRes>> getLiquorSnack(
            @RequestBody(required = true, description = "술 기본키") Long liquorPriKey
    ) {
        return ApiDataResponse.of(
                liquorViewService.getLiquorSnackList(liquorPriKey)
                        .stream()
                        .map(LiquorSnackRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "판매처 조회",
            notes = "술에 해당하는 판매처를 조회합니다."
    )
    @GetMapping(value = "/liquor-sell")
    public ApiDataResponse<List<LiquorSellDto>> getLiquorSell(
            @RequestBody(required = true, description = "술 기본키") Long liquorPriKey
    ) {
        return ApiDataResponse.of(
                liquorViewService.getLiquorSellList(liquorPriKey)
        );
    }
}
