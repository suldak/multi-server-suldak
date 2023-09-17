package com.sulsul.suldaksuldak.repo.bridge.sell;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QSlToLi.slToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorSell.liquorSell;

@Repository
@RequiredArgsConstructor
public class SlToLiRepositoryImpl implements SlToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndLiquorSellPriKey(
            Long liquorPriKey,
            Long liquorSellPriKey
    ) {
        return Optional.ofNullable(
                getBridgeDtoQuery()
                        .from(slToLi)
                        .innerJoin(slToLi.liquor, liquor)
                        .on(slToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(slToLi.liquorSell, liquorSell)
                        .on(slToLi.liquorSell.id.eq(liquorSellPriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<BridgeDto> getBridgeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                BridgeDto.class,
                                slToLi.id,
                                slToLi.liquor.id,
                                slToLi.liquor.name,
                                slToLi.liquorSell.id,
                                slToLi.liquorSell.name
                        )
                );
    }
}
