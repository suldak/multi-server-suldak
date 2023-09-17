package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.LiquorAbvDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class LiquorTotalRes {
    Long id;
    String name;
    String summaryExplanation;
    String detailExplanation;
    Long liquorAbvId;

    // 레시피
    LiquorRecipeRes liquorRecipeRes;
    // 추천 안주
    List<LiquorSnackRes> liquorSnackRes;
    // 도수
    LiquorAbvDto liquorAbvDto;


    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
}
