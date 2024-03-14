package com.sulsul.suldaksuldak.repo.stats.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.stats.QLiquorSearchLog.liquorSearchLog;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;

import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.dto.stats.search.LiquorSearchLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LiquorSearchLogRepositoryImpl implements LiquorSearchLogRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Long> findLiquorPriKeyByDateRange(
            Pageable pageable,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        List<Long> liquorPriKeyList =
                jpaQueryFactory
                        .select(liquorSearchLog.liquor.id)
                        .from(liquorSearchLog)
                        .innerJoin(liquorSearchLog.liquor, liquor)
                        .on(liquorSearchLog.liquor.id.eq(liquor.id))
                        .where(searchAtBetween(startAt, endAt), liquorIsActiveEq(true))
                        .groupBy(liquorSearchLog.liquor.id)
                        .orderBy(liquorSearchLog.liquor.count().desc(), liquorSearchLog.liquor.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Long> countQuery =
//                jpaQueryFactory.selectFrom(liquorSearchLog);
                jpaQueryFactory
                        .select(liquorSearchLog.liquor.id)
                        .from(liquorSearchLog)
                        .innerJoin(liquorSearchLog.liquor, liquor)
                        .on(liquorSearchLog.liquor.id.eq(liquor.id))
                        .where(searchAtBetween(startAt, endAt), liquorIsActiveEq(true))
                        .groupBy(liquorSearchLog.liquor.id);

        return PageableExecutionUtils.getPage(
                liquorPriKeyList, pageable,
                countQuery::fetchCount
        );
    }

    private JPAQuery<LiquorSearchLogDto> getLiquorSearchLogDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorSearchLogDto.class,
                                liquorSearchLog.id,
                                liquorSearchLog.liquor.id,
                                liquorSearchLog.searchAt
                        )
                );
    }

    private BooleanExpression searchAtBetween (
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return liquorSearchLog.searchAt.between(
                startAt, endAt
        );
    }

    public BooleanExpression liquorIsActiveEq(
            Boolean isActive
    ) {
        return liquorSearchLog.liquor.isActive.eq(isActive);
    }
}
