package com.sulsul.suldaksuldak.repo.party;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import static com.sulsul.suldaksuldak.domain.party.QParty.party;

import static com.sulsul.suldaksuldak.domain.user.QUser.user;

import com.sulsul.suldaksuldak.domain.file.QFileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartyRepositoryImpl implements PartyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PartyDto> findByOptional(
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            Pageable pageable
    ) {
        List<PartyDto> partyDtos =
                getPartyDtoQuery()
                        .from(party)
                        .innerJoin(party.user, user)
                        .on(hostUserPriKey == null ?
                                party.user.id.eq(user.id) :
                                party.user.id.eq(hostUserPriKey)
                        )
                        .leftJoin(party.fileBase, QFileBase.fileBase)
                        .where(
                                nameLike(name),
//                                introStrLike(searchStr),
                                searchAtBetween(searchStartTime, searchEndTime),
                                personnelEq(personnel),
                                partyTypeEq(partyType)
                        )
                        .orderBy(party.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Party> countQuery =
                jpaQueryFactory
                        .selectFrom(party)
                        .innerJoin(party.user, user)
                        .on(hostUserPriKey == null ?
                                party.user.id.eq(user.id) :
                                party.user.id.eq(hostUserPriKey)
                        )
                        .leftJoin(party.fileBase, QFileBase.fileBase)
                        .where(
                                nameLike(name),
//                                introStrLike(searchStr),
                                searchAtBetween(searchStartTime, searchEndTime),
                                personnelEq(personnel),
                                partyTypeEq(partyType)
                        );

        return PageableExecutionUtils.getPage(
                partyDtos, pageable,
                countQuery::fetchCount
        );
    }

    @Override
    public List<PartyDto> findByPriKeyList(
            List<Long> priKeyList
    ) {
        return getPartyDtoQuery()
                .from(party)
                .innerJoin(party.user, user)
                .on(party.user.id.eq(user.id))
                .leftJoin(party.fileBase, QFileBase.fileBase)
                .where(
                        party.id.in(priKeyList)
                )
                .fetch();
    }

    @Override
    public List<PartyDto> findByHostPriKey(
            Long hostPriKey
    ) {
        return getPartyDtoQuery()
                .from(party)
                .innerJoin(party.user, user)
                .on(party.user.id.eq(hostPriKey))
                .leftJoin(party.fileBase, QFileBase.fileBase)
                .fetch();
    }

    private JPAQuery<PartyDto> getPartyDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyDto.class,
                                party.id,
                                party.name,
                                party.meetingDay,
                                party.personnel,
                                party.introStr,
                                party.partyType,
                                party.partyPlace,
                                party.contactType,
                                party.useProgram,
                                party.onlineUrl,
                                party.user.id,
                                party.user.nickname,
                                party.fileBase.fileNm,
                                party.partyTag.id,
                                party.partyTag.name,
                                party.createdAt,
                                party.modifiedAt
                        )
                );
    }

    private BooleanExpression nameLike(
            String name
    ) {
        return StringUtils.hasText(name) ?
                party.name.contains(name) : null;
    }

    private BooleanExpression introStrLike(
            String introStr
    ) {
        return StringUtils.hasText(introStr) ?
                party.introStr.contains(introStr) : null;
    }

    private BooleanExpression searchAtBetween (
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return party.meetingDay.between(
                startAt,
                endAt
        );
    }

    private BooleanExpression personnelEq(
            Integer personnel
    ) {
        return personnel == null || personnel <= 0 ?
            null : party.personnel.eq(personnel);
    }

    private BooleanExpression partyTypeEq(
            PartyType partyType
    ) {
        return partyType == null ? null :
                party.partyType.eq(partyType);
    }
}
