package com.sulsul.suldaksuldak.repo.bridge.sell;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import com.sulsul.suldaksuldak.repo.bridge.BridgeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QSlToLi.slToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorSell.liquorSell;

@Repository
@RequiredArgsConstructor
public class SlToLiRepositoryImpl implements SlToLiRepositoryCustom, BridgeInterface {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndTagPriKey(
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

    @Override
    public List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(slToLi.liquor.id)
                .from(slToLi)
                .innerJoin(slToLi.liquor, liquor)
                .on(
                        liquorPriKeys == null || liquorPriKeys.isEmpty() ?
                                slToLi.liquor.id.eq(liquor.id) :
                                slToLi.liquor.id.in(liquorPriKeys)
                )
                .innerJoin(slToLi.liquorSell, liquorSell)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                slToLi.liquorSell.id.eq(liquorSell.id) :
                                slToLi.liquorSell.id.in(tagPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(slToLi.liquor.id)
                .from(slToLi)
                .innerJoin(slToLi.liquor, liquor)
                .on(slToLi.liquor.id.eq(liquor.id))
                .innerJoin(slToLi.liquorSell, liquorSell)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                slToLi.liquorSell.id.eq(liquorSell.id) :
                                slToLi.liquorSell.id.in(tagPriKeys)
                )
                .fetch();
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
