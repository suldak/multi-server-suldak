package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QDrinkingCapacity.drinkingCapacity;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorAbv.liquorAbv;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorDetail.liquorDetail;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorName.liquorName;

@Repository
@RequiredArgsConstructor
public class LiquorRepositoryImpl implements LiquorRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorDtoQuery()
                        .from(liquor)
                        .where(priKeyEq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<Long> findByLiquorAbvPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorAbv, liquorAbv)
                .on(liquor.liquorAbv.id.eq(priKey))
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorDetailPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorDetail, liquorDetail)
                .on(liquor.liquorDetail.id.eq(priKey))
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByDrinkingCapacityPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.drinkingCapacity, drinkingCapacity)
                .on(liquor.drinkingCapacity.id.eq(priKey))
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorNamePriKey(
            List<Long> liquorPriKeys,
            Long priKey
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorName, liquorName)
                .on(liquor.liquorName.id.eq(priKey))
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    private JPAQuery<LiquorDto> getLiquorDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorDto.class,
                                liquor.id,
                                liquor.name,
                                liquor.summaryExplanation,
                                liquor.detailExplanation,
                                liquor.liquorAbv.id,
                                liquor.liquorDetail.id,
                                liquor.drinkingCapacity.id,
                                liquor.liquorName.id,
                                liquor.createdAt,
                                liquor.modifiedAt
                        )
                );
    }

    private BooleanExpression priKeyEq(Long priKey) {
        return liquor.id.eq(priKey);
    }

    private BooleanExpression priKeyIn(
            List<Long> liquorPriKeys
    ) {
        return liquorPriKeys == null || liquorPriKeys.isEmpty() ? null :
                liquor.id.in(liquorPriKeys);
    }
}
