package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.*;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class LiquorTotalRes {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    // 레시피
    LiquorRecipeRes liquorRecipeRes;
    // 도수
    LiquorAbvDto liquorAbvDto;
    // 2차 분류
    LiquorDetailDto liquorDetailDto;
    // 주량
    DrinkingCapacityDto drinkingCapacityDto;
    // 1차 분루
    LiquorNameDto liquorNameDto;

    // 추천 안주
    List<LiquorSnackRes> liquorSnackRes;
    // 판매처
    List<LiquorSellDto> liquorSellDtos;
    // 재료
    List<LiquorMaterialDto> liquorMaterialDtos;
    // 기분
    List<StateTypeDto> stateTypeDtos;
    // 맛
    List<TasteTypeDto> tasteTypeDtos;

    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static LiquorTotalRes of (
            LiquorDto liquorDto,
            LiquorRecipeDto liquorRecipeDto,
            LiquorAbvDto liquorAbvDto,
            LiquorDetailDto liquorDetailDto,
            DrinkingCapacityDto drinkingCapacityDto,
            LiquorNameDto liquorNameDto,
            List<LiquorSnackDto> liquorSnackDtos,
            List<LiquorSellDto> liquorSellDtos,
            List<LiquorMaterialDto> liquorMaterialDtos,
            List<StateTypeDto> stateTypeDtos,
            List<TasteTypeDto> tasteTypeDtos
    ) {
        return new LiquorTotalRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                LiquorRecipeRes.from(liquorRecipeDto),
                liquorAbvDto,
                liquorDetailDto,
                drinkingCapacityDto,
                liquorNameDto,
                liquorSnackDtos.stream().map(LiquorSnackRes::from).collect(Collectors.toList()),
                liquorSellDtos,
                liquorMaterialDtos,
                stateTypeDtos,
                tasteTypeDtos,
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }
}
