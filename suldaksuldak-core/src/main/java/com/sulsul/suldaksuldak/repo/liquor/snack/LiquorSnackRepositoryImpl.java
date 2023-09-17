package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;

@Repository
@RequiredArgsConstructor
public class LiquorSnackRepositoryImpl implements LiquorSnackRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<LiquorSnackDto> findByLiquorPriKey(Long liquorPriKey) {
        return getLiquorSnackDtoQuery()
                .from(liquorSnack)
                .innerJoin(liquorSnack.snToLis, snToLi)
                .on(snToLi.liquor.id.eq(liquorPriKey))
                .fetch();
    }

    private JPAQuery<LiquorSnackDto> getLiquorSnackDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorSnackDto.class,
                                liquorSnack.id,
                                liquorSnack.name
                        )
                );
    }

    private BooleanExpression priKeyIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ? null : liquorSnack.id.in(priKeyList);
    }
}
