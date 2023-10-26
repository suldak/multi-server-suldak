package com.sulsul.suldaksuldak.repo.question.question;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LiquorQuestionRepositoryImpl implements LiquorQuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
