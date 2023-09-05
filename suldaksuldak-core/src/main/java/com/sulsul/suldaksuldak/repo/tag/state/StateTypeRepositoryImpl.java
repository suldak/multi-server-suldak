package com.sulsul.suldaksuldak.repo.tag.state;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StateTypeRepositoryImpl implements StateTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
