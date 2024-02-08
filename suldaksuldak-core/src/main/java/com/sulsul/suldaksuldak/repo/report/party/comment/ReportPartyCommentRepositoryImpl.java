package com.sulsul.suldaksuldak.repo.report.party.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.user.QUser;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.QPartyComment.partyComment;
import static com.sulsul.suldaksuldak.domain.report.QReportPartyComment.reportPartyComment;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReportPartyCommentRepositoryImpl
    implements ReportPartyCommentRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReportPartyCommentDto> findByOptions(
            Long userPriKey,
            Long partyPriKey,
            String commentPriKey,
            Long commentUserPriKey
    ) {
//        QUser commentUser = new QUser("commentUser");
        QUser commentUser = QUser.user;
        return getReportPartyCommentDtoQuery()
                .from(reportPartyComment)
                .innerJoin(reportPartyComment.user, user)
                .on(userPriKey == null ?
                        reportPartyComment.user.id.eq(user.id) :
                        reportPartyComment.user.id.eq(userPriKey)
                )
//                .innerJoin(reportPartyComment.partyComment.party, party)
                .innerJoin(reportPartyComment.partyComment, partyComment)
                .on(commentPriKey == null ?
                        reportPartyComment.partyComment.id.eq(partyComment.id) :
                        reportPartyComment.partyComment.id.eq(commentPriKey)
                )
//                .innerJoin(reportPartyComment.partyComment.user, commentUser)
                .on(commentUserPriKey == null ?
                        reportPartyComment.partyComment.user.id.eq(commentUser.id) :
                        reportPartyComment.partyComment.user.id.eq(commentUserPriKey)
                )
                .on(partyPriKey == null ?
                        reportPartyComment.partyComment.party.id.eq(party.id) :
                        reportPartyComment.partyComment.party.id.eq(partyPriKey)
                )
                .orderBy(reportPartyComment.createdAt.desc())
                .fetch();
    }

    private JPAQuery<ReportPartyCommentDto> getReportPartyCommentDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReportPartyCommentDto.class,
                                reportPartyComment.id,
                                reportPartyComment.user.id,
                                reportPartyComment.user.nickname,
                                reportPartyComment.user.fileBase.fileNm,
                                reportPartyComment.partyComment.party,
                                reportPartyComment.partyComment.id,
                                reportPartyComment.partyComment.comment,
                                reportPartyComment.partyComment.user,
                                reportPartyComment.createdAt,
                                reportPartyComment.modifiedAt
                        )
                );
    }
}
