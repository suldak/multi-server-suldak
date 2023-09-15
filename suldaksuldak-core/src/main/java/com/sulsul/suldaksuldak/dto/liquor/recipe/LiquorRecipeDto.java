package com.sulsul.suldaksuldak.dto.liquor.recipe;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.LiquorRecipe;
import lombok.Value;

@Value
public class LiquorRecipeDto {
    Long id;
    String content;
    Long liquorId;

    public static LiquorRecipeDto of (
            Long id,
            String content,
            Long liquorId
    ) {
        return new LiquorRecipeDto(
                id,
                content,
                liquorId
        );
    }

    public static LiquorRecipeDto of (LiquorRecipe liquorRecipe) {
        return new LiquorRecipeDto(liquorRecipe.getId(), liquorRecipe.getContent(), liquorRecipe.getLiquor().getId());
    }

    public LiquorRecipe toEntity(
            Liquor liquor
    ) {
        return LiquorRecipe.of(
                id,
                content,
                liquor
        );
    }

    public LiquorRecipe updateEntity(
            LiquorRecipe liquorRecipe
    ) {
        if (content != null) liquorRecipe.setContent(content);
        return liquorRecipe;
    }
}
