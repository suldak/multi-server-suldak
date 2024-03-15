package com.sulsul.suldaksuldak.repo.party.complete;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteDto;
import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.QPartyComplete.partyComplete;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PartyCompleteRepositoryImpl
    implements PartyCompleteRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartyCompleteDto> findByPartyPriKey(
            Long partyPriKey
    ) {
        return getPartyCompleteDtoQuery()
                .from(partyComplete)
                .innerJoin(partyComplete.party, party)
                .on(partyComplete.party.id.eq(partyPriKey))
                .innerJoin(partyComplete.user, user)
                .on(partyComplete.user.id.eq(user.id))
                .fetch();
    }

    @Override
    public Optional<PartyCompleteGroupDto> findUserGroupByUserPriKey(
            Long userPriKey,
            Boolean isCheckHost
    ) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        PartyCompleteGroupDto.class,
                                        partyComplete.user.id,
                                        partyComplete.isHost
                                                .when(true)
                                                .then(1L)
                                                .otherwise(0L).sum(),
                                        partyComplete.user.id.count()
                                )
                        )
                        .from(partyComplete)
                        .innerJoin(partyComplete.party, party)
                        .on(partyComplete.party.id.eq(party.id))
                        .innerJoin(partyComplete.user, user)
                        .on(partyComplete.user.id.eq(userPriKey))
                        .groupBy(partyComplete.user.id)
                        .where(
                                isCheckHost ?
                                        isHostProcessedEq(false) :
                                        isCompleteProcessedEq(false)

                        )
                        .fetchFirst()
        );
    }

    private JPAQuery<PartyCompleteDto> getPartyCompleteDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyCompleteDto.class,
                                partyComplete.id,
                                partyComplete.isCompleteProcessed,
                                partyComplete.isHostProcessed,
                                partyComplete.isHost,
                                partyComplete.processedAt,
                                partyComplete.party.id,
                                partyComplete.user.id
                        )
                );
    }

    private BooleanExpression isCompleteProcessedEq(
            Boolean isCompleteProcessed
    ) {
        return partyComplete.isCompleteProcessed.eq(isCompleteProcessed);
    }

    private BooleanExpression isHostProcessedEq(
            Boolean isHostProcessed
    ) {
        return partyComplete.isHostProcessed.eq(isHostProcessed);
    }
}
