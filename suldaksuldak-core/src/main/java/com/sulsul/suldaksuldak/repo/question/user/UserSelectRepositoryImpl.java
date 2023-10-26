package com.sulsul.suldaksuldak.repo.question.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSelectRepositoryImpl
        implements UserSelectRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;
}
