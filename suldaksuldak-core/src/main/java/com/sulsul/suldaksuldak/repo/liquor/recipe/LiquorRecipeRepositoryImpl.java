package com.sulsul.suldaksuldak.repo.liquor.recipe;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorRecipe.liquorRecipe;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LiquorRecipeRepositoryImpl implements LiquorRecipeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorRecipeDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorRecipeDtoQuery()
                        .from(liquorRecipe)
                        .innerJoin(liquorRecipe.liquor, liquor)
                        .on(liquorRecipe.liquor.id.eq(liquor.id))
                        .where(liquorRecipe.id.eq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<LiquorRecipeDto> findByLiquorPriKey(Long liquorPriKey) {
        return getLiquorRecipeDtoQuery()
                .from(liquorRecipe)
                .innerJoin(liquorRecipe.liquor, liquor)
                .on(liquorRecipe.liquor.id.eq(liquorPriKey))
                .fetch();
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
