package com.sulsul.suldaksuldak.repo.auth.party;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPartyFeedbackRepositoryImpl
    implements UserPartyFeedbackRepositoryCumstom
{
    private final JPAQueryFactory jpaQueryFactory;
}
