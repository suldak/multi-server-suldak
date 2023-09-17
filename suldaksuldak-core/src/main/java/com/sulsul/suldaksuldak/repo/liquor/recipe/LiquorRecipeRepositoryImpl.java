package com.sulsul.suldaksuldak.repo.liquor.recipe;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorRecipe.liquorRecipe;

@Repository
@RequiredArgsConstructor
public class LiquorRecipeRepositoryImpl implements LiquorRecipeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorRecipeDto> findByLiquorPriKey(Long liquorPriKey) {
        return Optional.ofNullable(
                getLiquorRecipeDtoQuery()
                        .from(liquorRecipe)
                        .innerJoin(liquorRecipe.liquor, liquor)
                        .on(liquorRecipe.liquor.id.eq(liquorPriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<LiquorRecipeDto> getLiquorRecipeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorRecipeDto.class,
                                liquorRecipe.id,
                                liquorRecipe.content,
                                liquorRecipe.liquor.id
                        )
                );
    }
}
