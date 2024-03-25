package com.sulsul.suldaksuldak.repo.tag.material;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorMaterialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.bridge.QMtToLi.mtToLi;
import static com.sulsul.suldaksuldak.domain.file.QFileBase.fileBase;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorMaterial.liquorMaterial;

@Repository
@RequiredArgsConstructor
public class LiquorMaterialRepositoryImpl implements LiquorMaterialRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LiquorMaterialDto> findByLiquorPriKey(Long liquorPriKey) {
        return getLiquorMaterialDtoQuery()
                .from(liquorMaterial)
                .innerJoin(liquorMaterial.mtToLis, mtToLi)
                .on(mtToLi.liquor.id.eq(liquorPriKey))
                .leftJoin(liquorMaterial.fileBase, fileBase)
                .fetch();
    }

    @Override
    public List<LiquorMaterialDto> findByPriKeyList(List<Long> priKeyList) {
        return getLiquorMaterialDtoQuery()
                .from(liquorMaterial)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<LiquorMaterialDto> getLiquorMaterialDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorMaterialDto.class,
                                liquorMaterial.id,
                                liquorMaterial.name,
                                liquorMaterial.fileBase.fileNm
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ?
                null : liquorMaterial.id.in(priKeyList);
    }
}
