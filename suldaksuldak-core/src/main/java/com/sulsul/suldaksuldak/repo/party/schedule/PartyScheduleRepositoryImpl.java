package com.sulsul.suldaksuldak.repo.party.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartyScheduleRepositoryImpl
    implements PartyScheduleRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;
}
