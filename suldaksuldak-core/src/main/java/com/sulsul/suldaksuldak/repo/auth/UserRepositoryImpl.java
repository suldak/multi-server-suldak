package com.sulsul.suldaksuldak.repo.auth;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserDto> findByUserEmail(
            String userEmail
    ) {
        return Optional.ofNullable(
                getUserDtoQuery()
                        .from(user)
                        .where(
                                userEmailEq(userEmail)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public Optional<UserDto> findByNickname(String nickname) {
        return Optional.ofNullable(
                getUserDtoQuery()
                        .from(user)
                        .where(
                                nicknameEq(nickname)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public Optional<UserDto> findUserBySocial(
            String email,
            String password,
            Registration registration
    ) {
        return Optional.ofNullable(
                getUserDtoQuery()
                        .from(user)
                        .where(
                                userEmailEq(email),
                                userPwEq(password),
                                userRegistrationEq(registration)
                        )
                        .fetchFirst()
        );
    }

    private JPAQuery<UserDto> getUserDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserDto.class,
                                user.id,
                                user.userEmail,
                                user.userPw,
                                user.nickname,
                                user.gender,
                                user.birthdayYear,
                                user.registration,
                                user.level,
                                user.warningCnt,
                                user.isActive,
                                user.selfIntroduction,
                                user.createdAt,
                                user.modifiedAt
                        )
                );
    }

    private BooleanExpression userEmailEq(
            String userEmail
    ) {
        return user.userEmail.eq(userEmail);
    }

    private BooleanExpression nicknameEq(
            String nickname
    ) {
        return user.nickname.eq(nickname);
    }

    private BooleanExpression userPwEq(
            String userPw
    ) {
        return user.userPw.eq(userPw);
    }

    private BooleanExpression userRegistrationEq(
            Registration registration
    ) {
        return user.registration.eq(registration);
    }
}
