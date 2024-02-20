package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.file.QFileBase.fileBase;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.tag.QDrinkingCapacity.drinkingCapacity;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorAbv.liquorAbv;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorDetail.liquorDetail;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorName.liquorName;
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
                        .leftJoin(liquor.fileBase, fileBase)
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
                .leftJoin(liquor.fileBase, fileBase)
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorAbvPriKey(
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
                .leftJoin(liquor.fileBase, fileBase)
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
                .leftJoin(liquor.fileBase, fileBase)
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorDetailPriKey(
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
                .leftJoin(liquor.fileBase, fileBase)
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
                .leftJoin(liquor.fileBase, fileBase)
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByDrinkingCapacityPriKey(
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
                .leftJoin(liquor.fileBase, fileBase)
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
                .leftJoin(liquor.fileBase, fileBase)
                .where(
                        priKeyIn(liquorPriKeys)
                )
                .fetch();
    }

    @Override
    public List<Long> findByLiquorNamePriKey(
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
                .leftJoin(liquor.fileBase, fileBase)
                .fetch();
    }

    @Override
    public List<Long> findBySearchTag(String searchTag) {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .leftJoin(liquor.fileBase, fileBase)
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
                        .leftJoin(liquor.fileBase, fileBase)
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
    public Page<LiquorDto> findByLiquorPriKeyListAndSearchTag(
            Pageable pageable,
            List<Long> liquorPriKeyList
    ) {
        JPAQuery<LiquorDto> selectQuery =
                getLiquorDtoQuery()
                        .from(liquor)
                        .leftJoin(liquor.fileBase, fileBase)
//                        .leftJoin(liquor.liquorAbv, liquorAbv)
//                        .on(liquor.liquorAbv.id.eq(liquorAbv.id))
//                        .leftJoin(liquor.liquorDetail, liquorDetail)
//                        .on(liquor.liquorDetail.id.eq(liquorDetail.id))
//                        .leftJoin(liquor.drinkingCapacity, drinkingCapacity)
//                        .on(liquor.drinkingCapacity.id.eq(drinkingCapacity.id))
//                        .leftJoin(liquor.liquorName, liquorName)
//                        .on(liquor.liquorName.id.eq(liquorName.id))
                        .where(priKeyIn(liquorPriKeyList));

        List<LiquorDto> liquorTotalRes =
                selectQuery
                        .orderBy(liquor.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<LiquorDto> countQuery = selectQuery
                .where(priKeyIn(liquorPriKeyList));

        return PageableExecutionUtils.getPage(
                liquorTotalRes, pageable,
                countQuery::fetchCount
        );
    }

    @Override
    public List<Long> findAllLiquorPriKey() {
        return jpaQueryFactory
                .select(liquor.id)
                .from(liquor)
                .leftJoin(liquor.fileBase, fileBase)
                .fetch();
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
                                liquor.fileBase.fileNm,
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
//        return liquorPriKeys == null || liquorPriKeys.isEmpty() ? null :
//                liquor.id.in(liquorPriKeys);
        return liquor.id.in(liquorPriKeys);
    }

    private BooleanExpression searchTagLike(
            String searchTag
    ) {
        return hasText(searchTag) ?
                liquor.searchTag.contains(searchTag) : null;
    }
}
