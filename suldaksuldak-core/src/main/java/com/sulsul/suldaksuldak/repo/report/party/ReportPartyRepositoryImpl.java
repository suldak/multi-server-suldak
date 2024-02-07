package com.sulsul.suldaksuldak.repo.report.party;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.report.QReportParty.reportParty;
import static com.sulsul.suldaksuldak.domain.report.QReportPartyReason.reportPartyReason;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReportPartyRepositoryImpl
    implements ReportPartyRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReportPartyDto> findByOption(
            Long userPriKey,
            Long partyPriKey,
            Long reportReasonPriKey,
            Boolean complete
    ) {
        return getReportPartyDtoQuery()
                .from(reportParty)
                .innerJoin(reportParty.user, user)
                .on(userPriKey == null ?
                        reportParty.user.id.eq(user.id) :
                        reportParty.user.id.eq(userPriKey)
                )
                .innerJoin(reportParty.party, party)
                .on(partyPriKey == null ?
                        reportParty.party.id.eq(party.id) :
                        reportParty.party.id.eq(partyPriKey)
                )
                .innerJoin(reportParty.reportReason, reportPartyReason)
                .on(reportReasonPriKey == null ?
                        reportParty.reportReason.id.eq(reportPartyReason.id) :
                        reportParty.reportReason.id.eq(reportReasonPriKey)
                )
                .where(completeEq(complete))
                .orderBy(reportParty.createdAt.desc())
                .fetch();
    }

    private JPAQuery<ReportPartyDto> getReportPartyDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReportPartyDto.class,
                                reportParty.id,
                                reportParty.user.id,
                                reportParty.user.nickname,
                                reportParty.user.fileBase.fileNm,
                                reportParty.party.id,
                                reportParty.party.name,
                                reportParty.party.fileBase.fileNm,
                                reportParty.reportReason.id,
                                reportParty.reportReason.reason,
                                reportParty.complete,
                                reportParty.createdAt,
                                reportParty.modifiedAt
                        )
                );
    }

    private BooleanExpression completeEq(
            Boolean complete
    ) {
        return complete == null ? null :
                reportParty.complete.eq(complete);
    }
}
