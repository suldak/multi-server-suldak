package com.sulsul.suldaksuldak.repo.tag.abv;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorAbvRepositoryImpl implements LiquorAbvRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

}
