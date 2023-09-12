package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorRepositoryImpl implements LiquorRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
