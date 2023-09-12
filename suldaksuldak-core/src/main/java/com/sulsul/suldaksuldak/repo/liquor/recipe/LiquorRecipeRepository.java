package com.sulsul.suldaksuldak.repo.liquor.recipe;

import com.sulsul.suldaksuldak.domain.liquor.LiquorRecipe;
import com.sulsul.suldaksuldak.domain.liquor.QLiquorRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorRecipeRepository extends
        JpaRepository<LiquorRecipe, Long>,
        LiquorRecipeRepositoryCustom,
        QuerydslPredicateExecutor<LiquorRecipe>,
        QuerydslBinderCustomizer<QLiquorRecipe>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorRecipe root) {
        bindings.excludeUnlistedProperties(true);
    }
}
