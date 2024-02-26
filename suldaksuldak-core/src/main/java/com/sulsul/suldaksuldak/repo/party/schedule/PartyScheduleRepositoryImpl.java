package com.sulsul.suldaksuldak.repo.party.schedule;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.batch.QPartySchedule.partySchedule;

@Repository
@RequiredArgsConstructor
public class PartyScheduleRepositoryImpl
    implements PartyScheduleRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PartySchedule> findByPartyPriKeyAndBatchType(
            Long partyPriKey,
            PartyBatchType partyBatchType
    ) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(partySchedule)
                        .innerJoin(partySchedule.party, party)
                        .on(partySchedule.party.id.eq(partyPriKey))
                        .where(partyBatchTypeEq(partyBatchType))
                        .fetchFirst()
        );
    }

    @Override
    public List<PartySchedule> findByPartyPriKeyAndIsActive(
            String myIp,
            Long partyPriKey
    ) {
        return jpaQueryFactory
                .selectFrom(partySchedule)
                .innerJoin(partySchedule.party, party)
                .on(partyPriKey == null ?
                        partySchedule.party.id.eq(party.id) :
                        partySchedule.party.id.eq(partyPriKey)
                )
                .where(
                        serverIpEq(myIp),
                        isActiveEq(true)
                )
                .fetch();
    }

    private BooleanExpression partyBatchTypeEq(
            PartyBatchType partyBatchType
    ) {
        return partyBatchType == null ? null :
                partySchedule.partyBatchType.eq(partyBatchType);
    }

    private BooleanExpression serverIpEq(
            String serverIp
    ) {
        return StringUtils.hasText(serverIp) ?
                partySchedule.serverIp.eq(serverIp) : null;
    }

    private BooleanExpression isActiveEq(
            Boolean isActive
    ) {
        return isActive == null ? null :
                partySchedule.isActive.eq(isActive);
    }
}
