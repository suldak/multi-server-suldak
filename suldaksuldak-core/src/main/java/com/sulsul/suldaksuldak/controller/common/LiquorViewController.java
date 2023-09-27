package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorRes;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.common.LiquorViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Long liquorPriKey
    ) {
        Optional<LiquorRecipeDto> liquorRecipeDto = liquorViewService.getLiquorRecipe(liquorPriKey);
        if (liquorRecipeDto.isEmpty()) {
            throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND RECIPE DATA");
        } else {
            return ApiDataResponse.of(LiquorRecipeRes.from(liquorRecipeDto.get()));
        }
    }

    @ApiOperation(
            value = "술에 관한 모든 정보 조회",
            notes = "술에 관한 모든 태그와 정보를 조회합니다."
    )
    @GetMapping(value = "/liquor")
    public ApiDataResponse<LiquorTotalRes> getLiquorTotalData (
            Long liquorPriKey
    ) {
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
}
