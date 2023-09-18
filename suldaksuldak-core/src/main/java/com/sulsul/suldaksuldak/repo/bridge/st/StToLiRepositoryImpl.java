package com.sulsul.suldaksuldak.repo.bridge.st;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QStToLi.stToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QStateType.stateType;

@Repository
@RequiredArgsConstructor
public class StToLiRepositoryImpl implements StToLiRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndStatePriKey(
            Long liquorPriKey,
            Long stateTypePriKey
    ) {
        return Optional.ofNullable(
                getBridgeDtoQuery()
                        .from(stToLi)
                        .innerJoin(stToLi.liquor, liquor)
                        .on(stToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(stToLi.stateType, stateType)
                        .on(stToLi.stateType.id.eq(stateTypePriKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(stToLi.liquor.id)
                .from(stToLi)
                .innerJoin(stToLi.liquor, liquor)
                .on(
                        liquorPriKeys == null || liquorPriKeys.isEmpty() ?
                                stToLi.liquor.id.eq(liquor.id) :
                                stToLi.liquor.id.in(liquorPriKeys)
                )
                .innerJoin(stToLi.stateType, stateType)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                stToLi.stateType.id.eq(stateType.id) :
                                stToLi.stateType.id.in(tagPriKeys)
                )
                .fetch();
    }

    private JPAQuery<BridgeDto> getBridgeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                BridgeDto.class,
                                stToLi.id,
                                stToLi.liquor.id,
                                stToLi.liquor.name,
                                stToLi.stateType.id,
                                stToLi.stateType.name
                        )
                );
    }
}
