package com.sulsul.suldaksuldak.repo.tag.sell;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorSellDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.bridge.QSlToLi.slToLi;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorSell.liquorSell;

@Repository
@RequiredArgsConstructor
public class LiquorSellRepositoryImpl implements LiquorSellRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LiquorSellDto> findByLiquorPriKey(Long liquorPriKey) {
        return getLiquorSellDtoQuery()
                .from(liquorSell)
                .innerJoin(liquorSell.slToLis, slToLi)
                .on(slToLi.liquor.id.eq(liquorPriKey))
                .fetch();
    }

    @Override
    public List<LiquorSellDto> findByPriKeyList(List<Long> priKeyList) {
        return getLiquorSellDtoQuery()
                .from(liquorSell)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<LiquorSellDto> getLiquorSellDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorSellDto.class,
                                liquorSell.id,
                                liquorSell.name
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ?
                null : liquorSell.id.in(priKeyList);
    }
}
