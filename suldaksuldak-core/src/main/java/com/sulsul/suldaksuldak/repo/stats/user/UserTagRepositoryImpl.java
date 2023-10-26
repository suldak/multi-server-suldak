package com.sulsul.suldaksuldak.repo.stats.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.dto.stats.user.UserTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.stats.QUserTag.userTag;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserTagRepositoryImpl
        implements UserTagRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserTagDto> findByUserPriKeyAndTagTypeAndTagId(
            Long userPriKey,
            TagType tagType,
            Long tagId
    ) {
        return Optional.ofNullable(
                getUserTagDtoQuery()
                        .from(userTag)
                        .innerJoin(userTag.user, user)
                        .on(userTag.user.id.eq(userPriKey))
                        .where(
                                tagTypeEq(tagType),
                                tagIdEq(tagId)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public List<UserTagDto> findByUserPriKey(
            Long userPriKey,
            Integer limitNum
    ) {
        return getUserTagDtoQuery()
                .from(userTag)
                .limit(limitNum)
                .innerJoin(userTag.user, user)
                .on(userTag.user.id.eq(userPriKey))
                .orderBy(userTag.weight.desc())
                .fetch();
    }

    private JPAQuery<UserTagDto> getUserTagDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserTagDto.class,
                                userTag.id,
                                userTag.tagType,
                                userTag.tagId,
                                userTag.weight,
                                userTag.user.id
                        )
                );
    }

    private BooleanExpression tagTypeEq(
            TagType tagType
    ) {
        return userTag.tagType.eq(tagType);
    }

    private BooleanExpression tagIdEq(
            Long tagId
    ) {
        return userTag.tagId.eq(tagId);
    }
}