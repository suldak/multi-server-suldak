package com.sulsul.suldaksuldak.repo.admin.auth;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.admin.QAdminUser.adminUser;
import com.sulsul.suldaksuldak.dto.auth.AdminUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminUserRepositoryImpl
        implements AdminUserRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<AdminUserDto> doLogin(
            String adminId,
            String adminPw
    ) {
        return Optional.ofNullable(
                getAdminUserDtoQuery()
                        .from(adminUser)
                        .where(
                                adminIdEq(adminId),
                                adminPwEq(adminPw)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public List<AdminUserDto> findAllAdminUser() {
        return getAdminUserDtoQuery()
                .from(adminUser)
                .fetch();
    }

    @Override
    public List<AdminUserDto> findByUserId(String adminId) {
        return getAdminUserDtoQuery()
                .from(adminUser)
                .where(adminIdEq(adminId))
                .fetch();
    }

    private JPAQuery<AdminUserDto> getAdminUserDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                AdminUserDto.class,
                                adminUser.id,
                                adminUser.adminId,
                                adminUser.adminPw,
                                adminUser.adminNm,
                                adminUser.createdAt,
                                adminUser.modifiedAt
                        )
                );
    }

    private BooleanExpression adminIdEq(
            String adminId
    ) {
        return adminUser.adminId.eq(adminId);
    }

    private BooleanExpression adminPwEq(
            String adminPw
    ) {
        return adminUser.adminPw.eq(adminPw);
    }
}
