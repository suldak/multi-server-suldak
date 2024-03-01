package com.sulsul.suldaksuldak.repo.admin.feedback;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.domain.user.QUser;
import com.sulsul.suldaksuldak.dto.admin.feedback.GroupUserFeedbackDto;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.sulsul.suldaksuldak.domain.admin.feedback.QUserPartyFeedback.userPartyFeedback;
import static com.sulsul.suldaksuldak.domain.party.QParty.party;

@Repository
@RequiredArgsConstructor
public class UserPartyFeedbackRepositoryImpl implements
        UserPartyFeedbackRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserPartyFeedbackDto> findByOption(
            FeedbackType feedbackType,
            String comment,
            Long partyPriKey,
            Long writerPriKey,
            Long targetUserPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        QUser writerUser = new QUser("writerUser");
        QUser targetUser = new QUser("targetUser");

        return getUserPartyFeedbackDtoQuery()
                .from(userPartyFeedback)
                .innerJoin(userPartyFeedback.party, party)
                .on(partyPriKey == null ?
                        userPartyFeedback.party.id.eq(party.id) :
                        userPartyFeedback.party.id.eq(partyPriKey)
                )
                .innerJoin(userPartyFeedback.writer, writerUser)
                .on(writerPriKey == null ?
                        userPartyFeedback.writer.id.eq(writerUser.id) :
                        userPartyFeedback.writer.id.eq(writerPriKey)
                )
                .innerJoin(userPartyFeedback.targetUser, targetUser)
                .on(targetUserPriKey == null ?
                        userPartyFeedback.targetUser.id.eq(targetUser.id) :
                        userPartyFeedback.targetUser.id.eq(targetUserPriKey)
                )
                .where(
                        feedbackTypeEq(feedbackType),
                        commentLike(comment),
                        feedbackAtBetween(startAt, endAt)
                )
                .orderBy(userPartyFeedback.feedbackAt.desc())
                .fetch();
    }

    @Override
    public List<Long> findAllUserPriKey() {
        QUser writerUser = new QUser("writerUser");
        QUser targetUser = new QUser("targetUser");
        return jpaQueryFactory
                .select(userPartyFeedback.targetUser.id)
                .from(userPartyFeedback)
                .innerJoin(userPartyFeedback.party, party)
                .on(userPartyFeedback.party.id.eq(party.id))
                .innerJoin(userPartyFeedback.writer, writerUser)
                .on(userPartyFeedback.writer.id.eq(writerUser.id))
                .innerJoin(userPartyFeedback.targetUser, targetUser)
                .on(userPartyFeedback.targetUser.id.eq(targetUser.id))
                .groupBy(userPartyFeedback.targetUser.id)
                .fetch();
    }

    @Override
    public List<GroupUserFeedbackDto> findGroupDtoByTargetPriKey(
            Long targetUserPriKey
    ) {
        QUser writerUser = new QUser("writerUser");
        QUser targetUser = new QUser("targetUser");
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                GroupUserFeedbackDto.class,
                                userPartyFeedback.targetUser.id,
                                userPartyFeedback.feedbackType,
                                userPartyFeedback.feedbackType.count()
                        )
                )
                .from(userPartyFeedback)
                .innerJoin(userPartyFeedback.party, party)
                .on(userPartyFeedback.party.id.eq(party.id))
                .innerJoin(userPartyFeedback.writer, writerUser)
                .on(userPartyFeedback.writer.id.eq(writerUser.id))
                .innerJoin(userPartyFeedback.targetUser, targetUser)
                .on(userPartyFeedback.targetUser.id.eq(targetUserPriKey))
                .groupBy(userPartyFeedback.feedbackType)
                .fetch();
    }

    private JPAQuery<UserPartyFeedbackDto> getUserPartyFeedbackDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserPartyFeedbackDto.class,
                                userPartyFeedback.id,
                                userPartyFeedback.feedbackType,
                                userPartyFeedback.comment,
                                userPartyFeedback.party.id,
                                userPartyFeedback.party.name,
                                userPartyFeedback.party.fileBase.fileNm,
                                userPartyFeedback.writer.id,
                                userPartyFeedback.writer.nickname,
                                userPartyFeedback.writer.fileBase.fileNm,
                                userPartyFeedback.targetUser.id,
                                userPartyFeedback.targetUser.nickname,
                                userPartyFeedback.targetUser.fileBase.fileNm,
                                userPartyFeedback.feedbackAt
                        )
                );
    }

    private BooleanExpression feedbackTypeEq(
            FeedbackType feedbackType
    ) {
        return feedbackType == null ? null :
                userPartyFeedback.feedbackType.eq(feedbackType);
    }

    private BooleanExpression commentLike(
            String comment
    ) {
        return StringUtils.hasText(comment) ?
                userPartyFeedback.comment.contains(comment) : null;
    }

    private BooleanExpression feedbackAtBetween(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return userPartyFeedback.feedbackAt.between(startAt, endAt);
    }
}
