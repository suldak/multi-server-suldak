package com.sulsul.suldaksuldak.repo.party.cancel;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.QParty.party;
import static com.sulsul.suldaksuldak.domain.party.cancel.QPartyCancel.partyCancel;
import static com.sulsul.suldaksuldak.domain.party.cancel.QPartyCancelReason.partyCancelReason;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class PartyCancelRepositoryImpl
    implements PartyCancelRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartyCancelDto> findByOptions(
            PartyRoleType partyRoleType,
            String detailReason,
            Long partyPriKey,
            Long userPriKeys,
            Long partyCancelReasonPriKey
    ) {
        return getPartyCancelDtoQuery()
                .from(partyCancel)
                .innerJoin(partyCancel.party, party)
                .on(partyPriKey == null ?
                        partyCancel.party.id.eq(party.id) :
                        partyCancel.party.id.eq(partyPriKey)
                )
                .innerJoin(partyCancel.user, user)
                .on(userPriKeys == null ?
                        partyCancel.user.id.eq(user.id) :
                        partyCancel.user.id.eq(userPriKeys)
                )
                .innerJoin(partyCancel.partyCancelReason, partyCancelReason)
                .on(partyCancelReasonPriKey == null ?
                        partyCancel.partyCancelReason.id.eq(partyCancelReason.id) :
                        partyCancel.partyCancelReason.id.eq(partyCancelReasonPriKey)
                )
                .where(
                        partyRoleTypeEq(partyRoleType),
                        detailReasonLike(detailReason)
                )
                .orderBy(partyCancel.createdAt.desc())
                .fetch();
    }

    private JPAQuery<PartyCancelDto> getPartyCancelDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyCancelDto.class,
                                partyCancel.id,
                                partyCancel.partyCancelReason.id,
                                partyCancel.partyCancelReason.partyRoleType,
                                partyCancel.partyCancelReason.reason,
                                partyCancel.detailReason,
                                partyCancel.party.id,
                                partyCancel.party.name,
                                partyCancel.party.fileBase.fileNm,
                                partyCancel.user.id,
                                partyCancel.user.nickname,
                                partyCancel.user.fileBase.fileNm
                        )
                );
    }

    private BooleanExpression partyRoleTypeEq(
            PartyRoleType partyRoleType
    ) {
        return partyRoleType == null ? null :
                partyCancel.partyCancelReason.partyRoleType.eq(partyRoleType);
    }

    private BooleanExpression detailReasonLike(
            String reason
    ) {
        return StringUtils.hasText(reason) ?
                partyCancel.detailReason.contains(reason) : null;
    }
}
