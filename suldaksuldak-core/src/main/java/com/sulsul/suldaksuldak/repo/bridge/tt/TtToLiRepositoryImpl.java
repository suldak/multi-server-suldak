package com.sulsul.suldaksuldak.repo.bridge.tt;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.bridge.QTtToLi.ttToLi;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;

import static com.sulsul.suldaksuldak.domain.tag.QTasteType.tasteType;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TtToLiRepositoryImpl implements TtToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndTastePriKey(
            Long liquorPriKey,
            Long tastePriKey
    ) {
        return Optional.ofNullable(
                getBridgeDtoQuery()
                        .from(ttToLi)
                        .innerJoin(ttToLi.liquor, liquor)
                        .on(ttToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(ttToLi.tasteType, tasteType)
                        .on(ttToLi.tasteType.id.eq(tastePriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<BridgeDto> getBridgeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                BridgeDto.class,
                                ttToLi.id,
                                ttToLi.liquor.id,
                                ttToLi.liquor.name,
                                ttToLi.tasteType.id,
                                ttToLi.tasteType.name
                        )
                );
    }
}
