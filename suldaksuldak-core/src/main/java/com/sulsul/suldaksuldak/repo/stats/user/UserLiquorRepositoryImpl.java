package com.sulsul.suldaksuldak.repo.stats.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public List<Long> findRatingByUserId(
            Long userPriKey,
            Integer limitNum
    ) {
        return jpaQueryFactory.select(userLiquor.liquor.id)
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
                                userLiquor.searchCnt
                        )
                );
    }
}
