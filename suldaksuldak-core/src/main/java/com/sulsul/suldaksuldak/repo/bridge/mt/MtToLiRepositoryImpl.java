package com.sulsul.suldaksuldak.repo.bridge.mt;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QMtToLi.mtToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorMaterial.liquorMaterial;

@Repository
@RequiredArgsConstructor
public class MtToLiRepositoryImpl implements MtToLiRepositoryCumstom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BridgeDto> findByLiquorPriKeyAndLiquorMaterialPriKey(
            Long liquorPriKey,
            Long liquorMaterialPriKey
    ) {
        return Optional.ofNullable(
                getBridgeDtoQuery()
                        .from(mtToLi)
                        .innerJoin(mtToLi.liquor, liquor)
                        .on(mtToLi.liquor.id.eq(liquorPriKey))
                        .innerJoin(mtToLi.liquorMaterial, liquorMaterial)
                        .on(mtToLi.liquorMaterial.id.eq(liquorMaterialPriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<BridgeDto> getBridgeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                BridgeDto.class,
                                mtToLi.id,
                                mtToLi.liquor.id,
                                mtToLi.liquor.name,
                                mtToLi.liquorMaterial.id,
                                mtToLi.liquorMaterial.name
                        )
                );
    }
}
