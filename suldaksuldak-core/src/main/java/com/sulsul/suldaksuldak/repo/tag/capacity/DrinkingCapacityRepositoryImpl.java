package com.sulsul.suldaksuldak.repo.tag.capacity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.DrinkingCapacityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.tag.QDrinkingCapacity.drinkingCapacity;

@Repository
@RequiredArgsConstructor
public class DrinkingCapacityRepositoryImpl implements DrinkingCapacityRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<DrinkingCapacityDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getDrinkingCapacityDtoQuery()
                        .from(drinkingCapacity)
                        .where(drinkingCapacity.id.eq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<DrinkingCapacityDto> findByPriKeyList(List<Long> priKeyList) {
        return getDrinkingCapacityDtoQuery()
                .from(drinkingCapacity)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<DrinkingCapacityDto> getDrinkingCapacityDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                DrinkingCapacityDto.class,
                                drinkingCapacity.id,
                                drinkingCapacity.name,
                                drinkingCapacity.color
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ?
                null : drinkingCapacity.id.in(priKeyList);
    }
}
