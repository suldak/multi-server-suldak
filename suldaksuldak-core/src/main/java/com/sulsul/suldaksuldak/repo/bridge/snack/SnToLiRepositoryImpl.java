package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import com.sulsul.suldaksuldak.repo.bridge.BridgeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;

@Repository
@RequiredArgsConstructor
public class SnToLiRepositoryImpl implements SnToLiRepositoryCustom, BridgeInterface {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndTagPriKey(
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

    @Override
    public Boolean deleteByLiquorPriKey(Long liquorPriKey) {
        jpaQueryFactory
                .delete(snToLi)
                .where(snToLi.liquor.id.eq(liquorPriKey))
                .execute();
        return true;
    }

    @Override
    public List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(snToLi.liquor.id)
                .from(snToLi)
                .innerJoin(snToLi.liquor, liquor)
                .on(
                        liquorPriKeys == null || liquorPriKeys.isEmpty() ?
                                snToLi.liquor.id.eq(liquor.id) :
                                snToLi.liquor.id.in(liquorPriKeys)
                )
                .innerJoin(snToLi.liquorSnack, liquorSnack)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                snToLi.liquorSnack.id.eq(liquorSnack.id) :
                                snToLi.liquorSnack.id.in(tagPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findLiquorPriKeyByTagPriKey(List<Long> tagPriKeys) {
        return jpaQueryFactory
                .select(snToLi.liquor.id)
                .from(snToLi)
                .innerJoin(snToLi.liquor, liquor)
                .on(snToLi.liquor.id.eq(liquor.id))
                .innerJoin(snToLi.liquorSnack, liquorSnack)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                snToLi.liquorSnack.id.eq(liquorSnack.id) :
                                snToLi.liquorSnack.id.in(tagPriKeys)
                )
                .fetch();
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
