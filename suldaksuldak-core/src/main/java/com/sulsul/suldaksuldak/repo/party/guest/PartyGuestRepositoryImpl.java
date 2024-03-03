package com.sulsul.suldaksuldak.repo.party.guest;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.file.QFileBase;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.QPartyGuest.partyGuest;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PartyGuestRepositoryImpl
        implements PartyGuestRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartyGuestDto> findByOptions(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            PartyType partyType,
            List<Long> partyTagPriList,
            Long partyPriKey,
            Long userPriKey,
            List<GuestType> confirmList
    ) {
        return getPartyGuestDtoQuery()
                .from(partyGuest)
                .innerJoin(partyGuest.party, party)
                .on(partyPriKey == null ?
                        partyGuest.party.id.eq(party.id) :
                        partyGuest.party.id.eq(partyPriKey)
                )
                .innerJoin(partyGuest.user, user)
                .on(userPriKey == null ?
                        partyGuest.user.id.eq(user.id) :
                        partyGuest.user.id.eq(userPriKey)
                )
                .leftJoin(partyGuest.user.fileBase, QFileBase.fileBase)
                .where(
                        searchAtBetween(searchStartTime, searchEndTime),
                        partyTypeEq(partyType),
                        partyTagListIn(partyTagPriList),
                        confirmIn(confirmList)
                )
                .orderBy(partyGuest.createdAt.asc())
                .fetch();
    }

    @Override
    public List<PartyGuest> findByPartyPriKey(
            Long partyPriKey,
            GuestType confirm
    ) {
        return jpaQueryFactory
                .selectFrom(partyGuest)
                .innerJoin(partyGuest.party, party)
                .on(partyGuest.party.id.eq(partyPriKey))
                .innerJoin(partyGuest.user, user)
                .on(partyGuest.user.id.eq(user.id))
                .leftJoin(partyGuest.user.fileBase, QFileBase.fileBase)
                .where(confirmEq(confirm))
                .fetch();
    }

    @Override
    public Optional<PartyGuest> findByUserPriKeyAndPartyPriKey(
            Long userPriKey,
            Long partyPriKey
    ) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(partyGuest)
                        .innerJoin(partyGuest.party, party)
                        .on(partyGuest.party.id.eq(partyPriKey))
                        .innerJoin(partyGuest.user, user)
                        .on(partyGuest.user.id.eq(userPriKey))
                        .leftJoin(partyGuest.user.fileBase, QFileBase.fileBase)
                        .fetchFirst()
        );
    }

    @Override
    public List<Long> findPartyPriKeyByTopGuestCount(
            Integer limitNum
    ) {
        return jpaQueryFactory
                .select(partyGuest.party.id)
                .from(partyGuest)
                .innerJoin(partyGuest.party, party)
                .on(partyGuest.party.id.eq(party.id))
                .where(
                        partyGuest.party.partyStateType.eq(
                                PartyStateType.RECRUITING
                        )
                )
                .groupBy(partyGuest.party.id)
                .orderBy(
                        partyGuest.party.id.count().desc(),
                        partyGuest.party.createdAt.desc()
                )
                .limit(limitNum)
                .fetch();
    }

    @Override
    public List<Long> findTahPriKeyByUserRecommend(
            Long userPriKey,
            Integer limitNum
    ) {
        return jpaQueryFactory
                .select(partyGuest.party.partyTag.id)
                .from(partyGuest)
                .innerJoin(partyGuest.party, party)
                .on(partyGuest.party.id.eq(party.id))
                .innerJoin(partyGuest.user, user)
                .on(partyGuest.user.id.eq(userPriKey))
                .where(
                        partyGuest.confirm.in(
                                List.of(
                                        GuestType.COMPLETE,
                                        GuestType.COMPLETE_WAIT,
                                        GuestType.ON_GOING,
                                        GuestType.CONFIRM
                                )
                        )
                )
                .groupBy(partyGuest.party.partyTag.id, partyGuest.modifiedAt)
                .limit(limitNum)
                .orderBy(partyGuest.modifiedAt.desc())
                .fetch();
    }

    private JPAQuery<PartyGuestDto> getPartyGuestDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyGuestDto.class,
                                partyGuest.id,
                                partyGuest.party.id,
                                partyGuest.party.name,
                                partyGuest.party.user,
//                                partyGuest.party.user.id,
//                                partyGuest.party.user.nickname,
//                                partyGuest.party.user.fileBase.fileNm,
                                partyGuest.user.id,
                                partyGuest.user.nickname,
                                partyGuest.user.fileBase.fileNm,
                                partyGuest.user.level,
                                partyGuest.confirm
                        )
                );
    }

    private BooleanExpression confirmIn(
            List<GuestType> confirmList
    ) {
        return confirmList == null || confirmList.isEmpty() ?
                null : partyGuest.confirm.in(confirmList);
    }

    private BooleanExpression confirmEq(
            GuestType confirm
    ) {
        return confirm == null ? null :
                partyGuest.confirm.eq(confirm);
    }

    private BooleanExpression searchAtBetween (
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return partyGuest.party.meetingDay.between(
                startAt,
                endAt
        );
    }

    private BooleanExpression partyTypeEq(
            PartyType partyType
    ) {
        return partyType == null ? null :
                partyGuest.party.partyType.eq(partyType);
    }

    private BooleanExpression partyTagListIn(
            List<Long> partyTagList
    ) {
        return partyTagList == null || partyTagList.isEmpty()  ? null :
                partyGuest.party.partyTag.id.in(partyTagList);
    }
}
