package com.sulsul.suldaksuldak.repo.stats.user.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import static com.sulsul.suldaksuldak.domain.stats.QUserLiquor.userLiquor;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserLiquorRepositoryImpl implements UserLiquorRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserLiquorTagDto> findRatingByUserId(
            Long userPriKey,
            Integer limitNum
    ) {
        return getUserLiquorTagDtoQuery()
                .from(userLiquor)
                .limit(limitNum)
                .innerJoin(userLiquor.user, user)
                .on(userLiquor.user.id.eq(userPriKey))
                .innerJoin(userLiquor.liquor, liquor)
                .on(userLiquor.liquor.id.eq(liquor.id))
                .orderBy(userLiquor.searchCnt.desc())
                .fetch();
    }

    @Override
    public Optional<UserLiquorDto> findByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    ) {
        return Optional.ofNullable(
                getUserLiquorDtoQuery()
                        .from(userLiquor)
                        .innerJoin(userLiquor.user, user)
                        .on(userLiquor.user.id.eq(userPriKey))
                        .innerJoin(userLiquor.liquor, liquor)
                        .on(userLiquor.liquor.id.eq(liquorPriKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<UserLiquorDto> getUserLiquorDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserLiquorDto.class,
                                userLiquor.id,
                                userLiquor.user.id,
                                userLiquor.liquor.id,
                                userLiquor.searchCnt,
                                userLiquor.lastSearchTime
                        )
                );
    }

    private JPAQuery<UserLiquorTagDto> getUserLiquorTagDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserLiquorTagDto.class,
                                userLiquor.id,
                                userLiquor.user.id,
                                userLiquor.liquor.id,
                                userLiquor.searchCnt,
                                userLiquor.liquor.liquorAbv.id,
                                userLiquor.liquor.liquorDetail.id,
                                userLiquor.liquor.drinkingCapacity.id,
                                userLiquor.liquor.liquorName.id
                        )
                );
    }
}
