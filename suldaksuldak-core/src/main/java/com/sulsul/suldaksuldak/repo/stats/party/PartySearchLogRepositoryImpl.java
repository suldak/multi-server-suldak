package com.sulsul.suldaksuldak.repo.stats.party;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.stats.QPartySearchLog.partySearchLog;

import static com.sulsul.suldaksuldak.domain.user.QUser.user;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import com.sulsul.suldaksuldak.dto.stats.party.PartySearchLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PartySearchLogRepositoryImpl
        implements PartySearchLogRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PartySearchLogDto> findLastByUserPriKeyAndPartyPriKey(
            Long userPriKey,
            Long partyPriKey
    ) {
        return Optional.ofNullable(
                getPartySearchLogDtoQuery()
                        .from(partySearchLog)
                        .innerJoin(partySearchLog.user, user)
                        .on(partySearchLog.user.id.eq(userPriKey))
                        .innerJoin(partySearchLog.party, party)
                        .on(partySearchLog.party.id.eq(partyPriKey))
                        .orderBy(partySearchLog.searchAt.desc())
                        .limit(1)
                        .fetchFirst()
        );
    }

    @Override
    public List<Long> findPartyPriKeyByTopSearch(
            Integer limitNum
    ) {
        return jpaQueryFactory
                .select(partySearchLog.party.id)
                .from(partySearchLog)
                .innerJoin(partySearchLog.user, user)
                .on(partySearchLog.user.id.eq(user.id))
                .innerJoin(partySearchLog.party, party)
                .on(partySearchLog.party.id.eq(party.id))
                .groupBy(partySearchLog.party.id)
                .limit(limitNum)
                .orderBy(partySearchLog.party.id.count().desc())
                .fetch();
    }

    private JPAQuery<PartySearchLogDto> getPartySearchLogDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartySearchLogDto.class,
                                partySearchLog.id,
                                partySearchLog.user.id,
                                partySearchLog.user.nickname,
                                partySearchLog.party.id,
                                partySearchLog.party.name,
                                partySearchLog.searchAt
                        )
                );
    }
}
