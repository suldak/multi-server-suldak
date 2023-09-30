package com.sulsul.suldaksuldak.repo.admin.consent;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;

import static com.sulsul.suldaksuldak.domain.admin.QConsentItem.consentItem;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConsentItemRepositoryImpl implements ConsentItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ConsentItemDto> findConsentList(
            ConsentItemType consentItemType,
            Integer itemSeq
    ) {
        return getConsentItemDtoQuery()
                .from(consentItem)
                .where(
                        itemTypeEq(consentItemType),
                        itemSeqEq(itemSeq)
                )
                .orderBy(consentItem.itemSeq.asc())
                .fetch();
    }

    private JPAQuery<ConsentItemDto> getConsentItemDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ConsentItemDto.class,
                                consentItem.id,
                                consentItem.itemType,
                                consentItem.itemSeq,
                                consentItem.itemText
                        )
                );
    }

    private BooleanExpression itemTypeEq(
            ConsentItemType itemType
    ) {
        return itemType == null ? null : consentItem.itemType.eq(itemType);
    }

    private BooleanExpression itemSeqEq(
            Integer itemSeq
    ) {
        return itemSeq == null || itemSeq < 0 ? null :
                consentItem.itemSeq.eq(itemSeq);
    }
}
