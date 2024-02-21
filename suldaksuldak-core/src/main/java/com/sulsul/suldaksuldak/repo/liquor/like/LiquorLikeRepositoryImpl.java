package com.sulsul.suldaksuldak.repo.liquor.like;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorLike.liquorLike;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class LiquorLikeRepositoryImpl
    implements LiquorLikeRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorLike> findByByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    ) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(liquorLike)
                        .innerJoin(liquorLike.user, user)
                        .on(liquorLike.user.id.eq(userPriKey))
                        .innerJoin(liquorLike.liquor, liquor)
                        .on(liquorLike.liquor.id.eq(liquorPriKey))
                        .fetchFirst()
        );
    }

    @Override
    public Page<LiquorLikeDto> findByLiquorPriKeyWithUserPriKey(
            Long liquorPriKey,
            Long userPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    ) {
        List<LiquorLikeDto> liquorLikeDtos =
                getLiquorLikeDtoQuery()
                        .from(liquorLike)
                        .innerJoin(liquorLike.liquor, liquor)
                        .on(liquorPriKey == null ?
                                liquorLike.liquor.id.eq(liquor.id) :
                                liquorLike.liquor.id.eq(liquorPriKey)
                        )
                        .innerJoin(liquorLike.user, user)
                        .on(userPriKey == null ?
                                liquorLike.user.id.eq(user.id) :
                                liquorLike.user.id.eq(userPriKey)
                        )
                        .where(likeAtBetween(startAt, endAt))
                        .groupBy(liquorLike.liquor.id)
                        .orderBy(
                                liquorLike.liquor.id.count().desc(),
                                liquorLike.liquor.createdAt.desc()
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        JPAQuery<LiquorLike> countQuery =
                jpaQueryFactory
                        .selectFrom(liquorLike)
                        .innerJoin(liquorLike.liquor, liquor)
                        .on(liquorPriKey == null ?
                                liquorLike.liquor.id.eq(liquor.id) :
                                liquorLike.liquor.id.eq(liquorPriKey)
                        )
                        .innerJoin(liquorLike.user, user)
                        .on(userPriKey == null ?
                                liquorLike.user.id.eq(user.id) :
                                liquorLike.user.id.eq(userPriKey)
                        )
                        .where(likeAtBetween(startAt, endAt))
                        .groupBy(liquorLike.liquor.id);
        return PageableExecutionUtils.getPage(
                liquorLikeDtos, pageable,
                countQuery::fetchCount
        );
    }

    private JPAQuery<LiquorLikeDto> getLiquorLikeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorLikeDto.class,
//                                liquorLike.id,
                                liquorLike.liquor.id,
                                liquorLike.liquor.name,
                                liquorLike.liquor.fileBase.fileNm,
//                                liquorLike.user.id,
//                                liquorLike.user.nickname,
//                                liquorLike.user.fileBase.fileNm,
//                                liquorLike.likeTime,
                                liquorLike.liquor.id.count()
                        )
                );
    }

    private BooleanExpression likeAtBetween(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return liquorLike.likeTime.between(startAt, endAt);
    }
}
