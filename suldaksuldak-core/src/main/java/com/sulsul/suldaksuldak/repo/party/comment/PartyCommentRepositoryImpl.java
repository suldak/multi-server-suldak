package com.sulsul.suldaksuldak.repo.party.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.QPartyComment.partyComment;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PartyCommentRepositoryImpl
    implements PartyCommentRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PartyCommentDto> findByPartyPriKey(
            Long partyPriKey,
            Pageable pageable
    ) {
        List<PartyCommentDto> partyCommentDtos =
                getPartyCommentDtoQuery()
                        .from(partyComment)
                        .innerJoin(partyComment.user, user)
                        .on(partyComment.user.id.eq(user.id))
                        .innerJoin(partyComment.party, party)
                        .on(partyComment.party.id.eq(partyPriKey))
                        .orderBy(partyComment.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .where(partyComment.commentDep.eq(0))
                        .fetch();
        JPAQuery<PartyComment> countQuery =
                jpaQueryFactory
                        .selectFrom(partyComment)
                        .innerJoin(partyComment.user, user)
                        .on(partyComment.user.id.eq(user.id))
                        .innerJoin(partyComment.party, party)
                        .on(partyComment.party.id.eq(partyPriKey))
                        .where(partyComment.commentDep.eq(0));
        return PageableExecutionUtils.getPage(
                partyCommentDtos, pageable,
                countQuery::fetchCount
        );

    }

    @Override
    public List<PartyCommentDto> findByCommentDep(
            Long partyPriKey,
            String groupComment,
            Integer commentDep
    ) {
        return getPartyCommentDtoQuery()
                .from(partyComment)
                .innerJoin(partyComment.user, user)
                .on(partyComment.user.id.eq(user.id))
                .innerJoin(partyComment.party, party)
                .on(partyComment.party.id.eq(partyPriKey))
                .orderBy(partyComment.createdAt.asc())
                .where(
                        partyComment.groupComment.eq(groupComment).and(
                                partyComment.commentDep.goe(commentDep)
                        )
                )
                .fetch();
    }

    private JPAQuery<PartyCommentDto> getPartyCommentDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyCommentDto.class,
                                partyComment.id,
                                partyComment.comment,
                                partyComment.user.id,
                                partyComment.user.nickname,
                                partyComment.user.fileBase.fileNm,
                                partyComment.party.id,
                                partyComment.party.name,
                                partyComment.party.fileBase.fileNm,
                                partyComment.groupComment,
//                                partyComment.groupComment.count(),
                                partyComment.commentDep,
                                partyComment.isDelete,
                                partyComment.isModified,
                                partyComment.warningCnt,
                                partyComment.createdAt,
                                partyComment.modifiedAt
                        )
                );
    }
}
