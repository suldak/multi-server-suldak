package com.sulsul.suldaksuldak.repo.tag.name;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorNameRepositoryImpl implements LiquorNameRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
