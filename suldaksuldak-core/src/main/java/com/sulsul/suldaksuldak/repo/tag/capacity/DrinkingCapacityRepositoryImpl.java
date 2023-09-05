package com.sulsul.suldaksuldak.repo.tag.capacity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DrinkingCapacityRepositoryImpl implements DrinkingCapacityRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
