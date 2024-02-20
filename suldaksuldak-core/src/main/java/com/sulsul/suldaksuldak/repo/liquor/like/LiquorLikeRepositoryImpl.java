package com.sulsul.suldaksuldak.repo.liquor.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquorLike.liquorLike;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;

import static com.sulsul.suldaksuldak.domain.user.QUser.user;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
