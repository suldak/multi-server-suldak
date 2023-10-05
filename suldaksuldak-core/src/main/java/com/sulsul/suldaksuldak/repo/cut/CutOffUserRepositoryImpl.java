package com.sulsul.suldaksuldak.repo.cut;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.domain.user.QUser;
import com.sulsul.suldaksuldak.dto.cut.CutOffUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.user.QCutOffUser.cutOffUser;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class CutOffUserRepositoryImpl implements CutOffUserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CutOffUserDto> findByUserId(Long userId) {
        return getCutOffUserDtoQuery()
                .from(cutOffUser)
                .innerJoin(cutOffUser.user, user)
                .on(cutOffUser.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Long> findByUserIdAndCutUserId(
            Long userId,
            Long cutUserId
    ) {
//        QUser my = new QUser("my");
//        QUser cutUser = new QUser("cutUser");
        return jpaQueryFactory
                .select(cutOffUser.id)
                .from(cutOffUser)
                .innerJoin(cutOffUser.user, user)
                .on(cutOffUser.user.id.eq(userId))
                .innerJoin(cutOffUser.cutUser, user)
                .on(cutOffUser.cutUser.id.eq(cutUserId))
//                .where(
//                        cutOffUser.cutUser.id.eq(cutUserId)
//                )
                .fetch();
    }

    @Override
    public List<Long> findCutUserIdByUserId(Long userId) {
        return jpaQueryFactory
                .select(cutOffUser.cutUser.id)
                .innerJoin(cutOffUser.user, user)
                .on(cutOffUser.user.id.eq(userId))
                .fetch();
    }

    private JPAQuery<CutOffUserDto> getCutOffUserDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                CutOffUserDto.class,
                                cutOffUser.id,
                                cutOffUser.user.id,
                                cutOffUser.cutUser.id,
                                cutOffUser.cutUser.nickname,
                                cutOffUser.cutUser.fileBase.fileNm,
                                cutOffUser.createdAt,
                                cutOffUser.modifiedAt
                        )
                );
    }
}
