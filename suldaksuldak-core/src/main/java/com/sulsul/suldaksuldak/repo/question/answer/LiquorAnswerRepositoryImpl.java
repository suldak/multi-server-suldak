package com.sulsul.suldaksuldak.repo.question.answer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorAnswerRepositoryImpl implements LiquorAnswerRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
