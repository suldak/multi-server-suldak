package com.sulsul.suldaksuldak.repo.party.feedback;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartyFeedbackRepositoryImpl
    implements PartyFeedbackRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;
}
