package com.sulsul.suldaksuldak.repo.tag.taste;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.TasteTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.bridge.QTtToLi.ttToLi;
import static com.sulsul.suldaksuldak.domain.tag.QTasteType.tasteType;

@Repository
@RequiredArgsConstructor
public class TasteTypeRepositoryImpl implements TasteTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TasteTypeDto> findByLiquorPriKey(Long liquorPriKey) {
        return getTasteTypeDtoQuery()
                .from(tasteType)
                .innerJoin(tasteType.ttToLis, ttToLi)
                .on(ttToLi.liquor.id.eq(liquorPriKey))
                .fetch();
    }

    @Override
    public List<TasteTypeDto> findByPriKeyList(List<Long> priKeyList) {
        return getTasteTypeDtoQuery()
                .from(tasteType)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<TasteTypeDto> getTasteTypeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                TasteTypeDto.class,
                                tasteType.id,
                                tasteType.name
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ?
                null : tasteType.id.in(priKeyList);
    }
}
