package com.sulsul.suldaksuldak.repo.party.cancel;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelReasonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.party.cancel.QPartyCancelReason.partyCancelReason;

@Repository
@RequiredArgsConstructor
public class PartyCancelReasonRepositoryImpl
    implements PartyCancelReasonRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartyCancelReasonDto> findByPartyRoleType(
            PartyRoleType partyRoleType
    ) {
        return getPartyCancelReasonDtoQuery()
                .from(partyCancelReason)
                .where(
                        partyRoleTypeEq(partyRoleType)
                )
                .fetch();
    }

    private JPAQuery<PartyCancelReasonDto> getPartyCancelReasonDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PartyCancelReasonDto.class,
                                partyCancelReason.id,
                                partyCancelReason.reason,
                                partyCancelReason.partyRoleType
                        )
                );
    }

    private BooleanExpression partyRoleTypeEq(
            PartyRoleType partyRoleType
    ) {
        return partyRoleType == null ? null :
                partyCancelReason.partyRoleType.eq(partyRoleType);
    }
}
