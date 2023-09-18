package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;

@Repository
@RequiredArgsConstructor
public class SnToLiRepositoryImpl implements SnToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndLiquorSnackPriKey(
            Long liquorPriKey,
            Long liquorSnackPriKey
    ) {
        return Optional.ofNullable(
                getBridgeDtoQuery()
                        .from(snToLi)
                        .innerJoin(snToLi.liquor, liquor)
                        .on(snToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(snToLi.liquorSnack, liquorSnack)
                        .on(snToLi.liquorSnack.id.eq(liquorSnackPriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<BridgeDto> getBridgeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                BridgeDto.class,
                                snToLi.id,
                                snToLi.liquor.id,
                                snToLi.liquor.name,
                                snToLi.liquorSnack.id,
                                snToLi.liquorSnack.name
                        )
                );
    }
}
