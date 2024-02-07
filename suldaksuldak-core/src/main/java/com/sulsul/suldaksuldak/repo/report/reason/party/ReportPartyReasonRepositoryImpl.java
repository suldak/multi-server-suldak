package com.sulsul.suldaksuldak.repo.report.reason.party;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportPartyReasonRepositoryImpl
    implements ReportPartyReasonRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;
}
