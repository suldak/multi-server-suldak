package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorSnackRepositoryImpl implements LiquorSnackRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
