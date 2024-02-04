package com.sulsul.suldaksuldak.repo.party.tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartyTagRepositoryImpl
    implements PartyTagRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;
}
