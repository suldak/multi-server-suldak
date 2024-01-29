package com.sulsul.suldaksuldak.repo.tag.capacity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.DrinkingCapacityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
