package com.sulsul.suldaksuldak.repo.party;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.file.QFileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.party.PartyTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.QPartyGuest.partyGuest;
import static com.sulsul.suldaksuldak.domain.report.QReportParty.reportParty;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PartyRepositoryImpl implements PartyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PartyDto> findByOptional(
            List<Long> notShowParty,
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            List<Long> partyTagPriList,
            List<PartyStateType> partyStateTypes,
            Boolean sortBool,
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
                                partyTypeEq(partyType),
                                partyTagListIn(partyTagPriList),
                                partyStateTypeEq(partyStateTypes),
                                notInPartyPriKey(notShowParty)
                        )
                        .orderBy(
                                (sortBool == null || sortBool) ?
                                        party.createdAt.desc() :
                                        party.createdAt.asc()
                        )
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
                                partyTypeEq(partyType),
                                partyTagListIn(partyTagPriList),
                                partyStateTypeEq(partyStateTypes),
                                notInPartyPriKey(notShowParty)
                        );

        return PageableExecutionUtils.getPage(
                partyDtos, pageable,
                countQuery::fetchCount
        );
    }

    @Override
    public List<PartyDto> findByPriKeyList(
            List<Long> priKeyList,
            Boolean sortBool
    ) {
        return getPartyDtoQuery()
                .from(party)
                .innerJoin(party.user, user)
                .on(party.user.id.eq(user.id))
                .leftJoin(party.fileBase, QFileBase.fileBase)
                .where(
                        party.id.in(priKeyList)
                )
                .orderBy(
                        (sortBool == null || sortBool) ?
                                party.createdAt.desc() :
                                party.createdAt.asc()
                )
                .fetch();
    }

    @Override
    public List<PartyTotalDto> findByPriKeyAndGuestPriKey(
            List<Long> priKeyList,
            Long guestPriKey,
            Boolean sortBool
    ) {
        return getPartyTotalDtoQuery(guestPriKey)
                .from(party)
                .innerJoin(party.user, user)
                .on(party.user.id.eq(user.id))
                .leftJoin(party.fileBase, QFileBase.fileBase)
                .where(
                        party.id.in(priKeyList)
                )
                .orderBy(
                        (sortBool == null || sortBool) ?
                                party.createdAt.desc() :
                                party.createdAt.asc()
                )
                .fetch();
    }

    @Override
    public List<PartyDto> findByHostPriKey(
            Long hostPriKey,
            Boolean sortBool
    ) {
        return getPartyDtoQuery()
                .from(party)
                .innerJoin(party.user, user)
                .on(party.user.id.eq(hostPriKey))
                .leftJoin(party.fileBase, QFileBase.fileBase)
                .orderBy(
                        (sortBool == null || sortBool) ?
                                party.createdAt.desc() :
                                party.createdAt.asc()
                )
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
                                party.partyStateType,
                                party.user.id,
                                party.user.nickname,
                                party.user.fileBase.fileNm,
                                party.fileBase.fileNm,
                                party.partyTag.id,
                                party.partyTag.name,
                                JPAExpressions.select(reportParty.count())
                                        .from(reportParty)
                                        .where(
                                                reportParty.party.id.eq(party.id)
                                        ),
                                party.createdAt,
                                party.modifiedAt,
                                JPAExpressions.select(partyGuest.count())
                                        .from(partyGuest)
                                        .where(
                                                partyGuest.party.id.eq(party.id)
                                                        .and(
                                                                partyConfirmCnt()
                                                        )
                                        )
                        )
                );
    }

    private JPAQuery<PartyTotalDto> getPartyTotalDtoQuery(
            Long guestPriKey
    ) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyTotalDto.class,
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
                                party.partyStateType,
                                party.user.id,
                                party.user.nickname,
                                party.user.fileBase.fileNm,
                                party.fileBase.fileNm,
                                party.partyTag.id,
                                party.partyTag.name,
                                JPAExpressions.select(reportParty.count())
                                        .from(reportParty)
                                        .where(
                                                reportParty.party.id.eq(party.id)
                                        ),
                                party.createdAt,
                                party.modifiedAt,
                                JPAExpressions.select(partyGuest.count())
                                        .from(partyGuest)
                                        .where(
                                                partyGuest.party.id.eq(party.id)
                                                        .and(
                                                                partyConfirmCnt()
                                                        )
                                        ),
                                JPAExpressions.select(partyGuest.confirm)
                                        .from(partyGuest)
                                        .where(
                                                partyGuest.party.id.eq(party.id)
                                                        .and(
                                                                partyGuest.user.id.eq(guestPriKey)
                                                        )
                                        )
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

    public BooleanExpression partyTagListIn(
            List<Long> partyTagPriList
    ) {
        return partyTagPriList == null || partyTagPriList.isEmpty() ? null :
                party.partyTag.id.in(partyTagPriList);
    }

    public BooleanBuilder partyConfirmCnt() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(partyGuest.confirm.eq(GuestType.CONFIRM));
        booleanBuilder.or(partyGuest.confirm.eq(GuestType.COMPLETE));
        booleanBuilder.or(partyGuest.confirm.eq(GuestType.COMPLETE_WAIT));
        return booleanBuilder;
    }

    public BooleanExpression partyStateTypeEq(
            List<PartyStateType> partyStateTypes
    ) {
        if (partyStateTypes == null || partyStateTypes.isEmpty())
            return null;
        return party.partyStateType.in(partyStateTypes);
    }

    public BooleanExpression notInPartyPriKey(
            List<Long> partyPriKey
    ) {
        if (partyPriKey == null || partyPriKey.isEmpty())
            return null;
        return party.id.notIn(partyPriKey);
    }
}
