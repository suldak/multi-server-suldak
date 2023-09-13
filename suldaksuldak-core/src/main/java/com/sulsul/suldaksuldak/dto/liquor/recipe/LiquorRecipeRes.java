package com.sulsul.suldaksuldak.dto.liquor.recipe;

import lombok.Value;

@Value
public class LiquorRecipeRes {
    Long id;
    String content;
    Long liquorId;

    public static LiquorRecipeRes from (
            LiquorRecipeDto liquorRecipeDto
    ) {
        return new LiquorRecipeRes(
                liquorRecipeDto.getId(),
                liquorRecipeDto.getContent(),
                liquorRecipeDto.getLiquorId()
        );
    }
}
