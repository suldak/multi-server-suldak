package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.bridge.QMtToLi.mtToLi;
import static com.sulsul.suldaksuldak.domain.bridge.QSlToLi.slToLi;
import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;
import static com.sulsul.suldaksuldak.domain.bridge.QStToLi.stToLi;
import static com.sulsul.suldaksuldak.domain.bridge.QTtToLi.ttToLi;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;
import static com.sulsul.suldaksuldak.domain.tag.QDrinkingCapacity.drinkingCapacity;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorAbv.liquorAbv;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorDetail.liquorDetail;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorMaterial.liquorMaterial;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorName.liquorName;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorSell.liquorSell;
import static com.sulsul.suldaksuldak.domain.tag.QStateType.stateType;
import static com.sulsul.suldaksuldak.domain.tag.QTasteType.tasteType;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class LiquorRepositoryImpl implements LiquorRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorDtoQuery()
                        .from(liquor)
                        .where(priKeyEq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<Long> findByLiquorAbvPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorAbv, liquorAbv)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                liquor.liquorAbv.id.eq(liquorAbv.id) :
                                liquor.liquorAbv.id.in(tagPriKeys)
                )
//                .where(
//                        priKeyIn(liquorPriKeys)
//                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorDetailPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorDetail, liquorDetail)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                liquor.liquorDetail.id.eq(liquorDetail.id) :
                                liquor.liquorDetail.id.in(tagPriKeys)
                )
//                .where(
//                        priKeyIn(liquorPriKeys)
//                )
                .fetch();
    }

    @Override
    public List<Long> findByDrinkingCapacityPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.drinkingCapacity, drinkingCapacity)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                liquor.drinkingCapacity.id.eq(drinkingCapacity.id) :
                                liquor.drinkingCapacity.id.in(tagPriKeys)
                )
//                .where(
//                        priKeyIn(liquorPriKeys)
//                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorNamePriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    ) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .innerJoin(liquor.liquorName, liquorName)
                .on(
                        tagPriKeys == null || tagPriKeys.isEmpty() ?
                                liquor.liquorName.id.eq(liquorName.id) :
                                liquor.liquorName.id.in(tagPriKeys)
                )
//                .where(
//                        priKeyIn(liquorPriKeys)
//                )
                .fetch();
    }

    @Override
    public List<Long> findBySearchTag(String searchTag) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .where(searchTagLike(searchTag))
                .orderBy(liquor.name.asc())
                .fetch();
    }

    @Override
    public Page<Long> findByCreatedLatest(
            Pageable pageable
    ) {
        List<Long> liquorDtos =
                jpaQueryFactory
                        .select(liquor.id)
                        .from(liquor)
                        .orderBy(liquor.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Liquor> countQuery = jpaQueryFactory.selectFrom(liquor);

        return PageableExecutionUtils.getPage(
                liquorDtos, pageable,
                countQuery::fetchCount
        );
    }

    @Override
    public Page<LiquorTotalRes> findByTags(
            Pageable pageable,
            List<Long> snackPriKeys,
            List<Long> sellPriKeys,
            List<Long> materialPriKeys,
            List<Long> statePriKeys,
            List<Long> tastePriKeys,
            List<Long> liquorAbvPriKeys,
            List<Long> liquorDetailPriKeys,
            List<Long> drinkingCapacityPriKeys,
            List<Long> liquorNamePriKeys,
            String searchTag
    ) {
        List<LiquorTotalRes> liquorTotalRes =
                getLiquorTotalResQuery()
                        .from(liquor)
                        .innerJoin(liquor.liquorAbv, liquorAbv)
                        .on(
                                liquorAbvPriKeys == null || liquorAbvPriKeys.isEmpty() ?
                                        liquor.liquorAbv.id.eq(liquorAbv.id) :
                                        liquor.liquorAbv.id.in(liquorAbvPriKeys)
                        )
                        .innerJoin(liquor.liquorDetail, liquorDetail)
                        .on(
                                liquorDetailPriKeys == null || liquorDetailPriKeys.isEmpty() ?
                                        liquor.liquorDetail.id.eq(liquorDetail.id) :
                                        liquor.liquorDetail.id.in(liquorDetailPriKeys)
                        )
                        .innerJoin(liquor.drinkingCapacity, drinkingCapacity)
                        .on(
                                drinkingCapacityPriKeys == null || drinkingCapacityPriKeys.isEmpty() ?
                                        liquor.drinkingCapacity.id.eq(drinkingCapacity.id) :
                                        liquor.drinkingCapacity.id.in(drinkingCapacityPriKeys)
                        )
                        .innerJoin(liquor.liquorName, liquorName)
                        .on(
                                liquorNamePriKeys == null || liquorNamePriKeys.isEmpty() ?
                                        liquor.liquorName.id.eq(liquorName.id) :
                                        liquor.liquorName.id.in(liquorNamePriKeys)
                        )
                        .innerJoin(snToLi.liquorSnack, liquorSnack)
                        .on(
                                snackPriKeys == null || snackPriKeys.isEmpty() ?
                                        snToLi.liquorSnack.id.eq(liquorSnack.id) :
                                        snToLi.liquorSnack.id.in(snackPriKeys)
                        )
                        .innerJoin(slToLi.liquorSell, liquorSell)
                        .on(
                                sellPriKeys == null || sellPriKeys.isEmpty() ?
                                        slToLi.liquorSell.id.eq(liquorSell.id) :
                                        slToLi.liquorSell.id.in(sellPriKeys)
                        )
                        .innerJoin(mtToLi.liquorMaterial, liquorMaterial)
                        .on(
                                materialPriKeys == null || materialPriKeys.isEmpty() ?
                                        mtToLi.liquorMaterial.id.eq(liquorMaterial.id) :
                                        mtToLi.liquorMaterial.id.in(materialPriKeys)
                        )
                        .innerJoin(stToLi.stateType, stateType)
                        .on(
                                statePriKeys == null || statePriKeys.isEmpty() ?
                                        stToLi.stateType.id.eq(stateType.id) :
                                        stToLi.stateType.id.in(statePriKeys)
                        )
                        .innerJoin(ttToLi.tasteType, tasteType)
                        .on(
                                tastePriKeys == null || tastePriKeys.isEmpty() ?
                                        ttToLi.tasteType.id.eq(tasteType.id) :
                                        ttToLi.tasteType.id.in(tastePriKeys)
                        )
                        .where(searchTagLike(searchTag))
                        .orderBy(liquor.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Liquor> countQuery = jpaQueryFactory.selectFrom(liquor);

        return PageableExecutionUtils.getPage(
                liquorTotalRes, pageable,
                countQuery::fetchCount
        );
    }

    private JPAQuery<LiquorDto> getLiquorDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorDto.class,
                                liquor.id,
                                liquor.name,
                                liquor.summaryExplanation,
                                liquor.detailExplanation,
                                liquor.searchTag,
                                liquor.liquorRecipe,
                                liquor.detailAbv,
                                liquor.liquorAbv.id,
                                liquor.liquorDetail.id,
                                liquor.drinkingCapacity.id,
                                liquor.liquorName.id,
                                liquor.createdAt,
                                liquor.modifiedAt
                        )
                );
    }

    private JPAQuery<LiquorTotalRes> getLiquorTotalResQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorTotalRes.class,
                                liquor.id,
                                liquor.name,
                                liquor.summaryExplanation,
                                liquor.detailExplanation,
                                liquor.liquorRecipe,
                                liquor.detailAbv,
                                Projections.bean(LiquorAbvDto.class, liquorAbv.id, liquorAbv.name),
                                Projections.bean(LiquorDetailDto.class, liquorDetail.id, liquorDetail.name),
                                Projections.bean(DrinkingCapacityDto.class, drinkingCapacity.id, drinkingCapacity.name),
                                Projections.bean(LiquorNameDto.class, liquorName.id, liquorName.name),
                                Projections.list(Projections.bean(LiquorSnackRes.class, snToLi.liquorSnack.id, snToLi.liquorSnack.name)),
                                Projections.list(Projections.bean(LiquorSellDto.class, slToLi.liquorSell.id, slToLi.liquorSell.name)),
                                Projections.list(Projections.bean(LiquorMaterialDto.class, mtToLi.liquorMaterial.id, mtToLi.liquorMaterial.name)),
                                Projections.list(Projections.bean(StateTypeDto.class, stToLi.stateType.id, stToLi.stateType.name)),
                                Projections.list(Projections.bean(TasteTypeDto.class, ttToLi.tasteType.id, ttToLi.tasteType.name)),
                                liquor.createdAt,
                                liquor.modifiedAt
                        )
                );
    }

    private BooleanExpression priKeyEq(Long priKey) {
        return liquor.id.eq(priKey);
    }

    private BooleanExpression priKeyIn(
            List<Long> liquorPriKeys
    ) {
        return liquorPriKeys == null || liquorPriKeys.isEmpty() ? null :
                liquor.id.in(liquorPriKeys);
    }

    private BooleanExpression searchTagLike(
            String searchTag
    ) {
        return hasText(searchTag) ?
                liquor.searchTag.contains(searchTag) : null;
    }
}
