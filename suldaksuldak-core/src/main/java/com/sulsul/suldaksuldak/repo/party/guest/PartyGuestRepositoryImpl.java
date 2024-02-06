package com.sulsul.suldaksuldak.repo.party.guest;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.domain.file.QFileBase;
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
            GuestType confirm
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
                                partyGuest.party.user,
//                                partyGuest.party.user.id,
//                                partyGuest.party.user.nickname,
//                                partyGuest.party.user.fileBase.fileNm,
                                partyGuest.user.id,
                                partyGuest.user.nickname,
                                partyGuest.user.fileBase.fileNm,
                                partyGuest.confirm
                        )
                );
    }

    private BooleanExpression confirmEq(
            GuestType confirm
    ) {
        return confirm == null ? null :
                partyGuest.confirm.eq(confirm);
    }
}
