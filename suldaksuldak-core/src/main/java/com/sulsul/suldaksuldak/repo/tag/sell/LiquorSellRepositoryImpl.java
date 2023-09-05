package com.sulsul.suldaksuldak.repo.tag.sell;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorSellRepositoryImpl implements LiquorSellRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
