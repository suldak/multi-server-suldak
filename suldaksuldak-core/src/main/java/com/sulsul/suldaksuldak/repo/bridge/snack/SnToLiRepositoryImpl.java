package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;
import com.sulsul.suldaksuldak.dto.bridge.SnToLiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SnToLiRepositoryImpl implements SnToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<SnToLiDto> findByLiquorPriKeyAndLiquorSnackPriKey(
            Long liquorPriKey,
            Long liquorSnackPriKey
    ) {
        return Optional.ofNullable(
                getSnToLiDtoQuery()
                        .from(snToLi)
                        .innerJoin(snToLi.liquor, liquor)
                        .on(snToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(snToLi.liquorSnack, liquorSnack)
                        .on(snToLi.liquorSnack.id.eq(liquorSnackPriKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<SnToLiDto> findByLiquorSnackPriKey(Long liquorSnackPriKey) {
        return getSnToLiDtoQuery()
                .from(snToLi)
                .innerJoin(snToLi.liquorSnack, liquorSnack)
                .on(snToLi.liquorSnack.id.eq(liquorSnackPriKey))
                .fetch();
    }

    @Override
    public List<SnToLiDto> findByLiquorPriKey(Long liquorPriKey) {
        return getSnToLiDtoQuery()
                .from(snToLi)
                .innerJoin(snToLi.liquor, liquor)
                .on(snToLi.liquor.id.eq(liquorPriKey))
                .fetch();
    }

    private JPAQuery<SnToLiDto> getSnToLiDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                SnToLiDto.class,
                                snToLi.id,
                                snToLi.liquor.id,
                                snToLi.liquor.name,
                                snToLi.liquorSnack.id,
                                snToLi.liquorSnack.name
                        )
                );
    }
}
