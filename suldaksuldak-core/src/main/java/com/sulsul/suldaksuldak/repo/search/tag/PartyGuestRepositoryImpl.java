package com.sulsul.suldaksuldak.repo.search.tag;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            Long partyPriKey,
            Long userPriKey,
            Boolean confirm
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
                .where(confirmEq(confirm))
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
                                partyGuest.user.id,
                                partyGuest.user.nickname,
                                partyGuest.confirm
                        )
                );
    }

    private BooleanExpression confirmEq(
            Boolean confirm
    ) {
        return confirm == null ? null :
                partyGuest.confirm.eq(confirm);
    }
}
