package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SnToLiRepositoryImpl implements SnToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
