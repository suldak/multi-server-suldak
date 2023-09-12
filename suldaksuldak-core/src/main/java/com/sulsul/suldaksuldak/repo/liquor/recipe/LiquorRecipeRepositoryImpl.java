package com.sulsul.suldaksuldak.repo.liquor.recipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorRecipeRepositoryImpl implements LiquorRecipeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
