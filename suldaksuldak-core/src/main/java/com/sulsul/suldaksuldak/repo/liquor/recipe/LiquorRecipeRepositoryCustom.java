package com.sulsul.suldaksuldak.repo.liquor.recipe;

import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;

import java.util.List;
import java.util.Optional;

public interface LiquorRecipeRepositoryCustom {
    Optional<LiquorRecipeDto> findByPriKey(
            Long priKey
    );
    List<LiquorRecipeDto> findByLiquorPriKey(
            Long liquorPriKey
    );
}
