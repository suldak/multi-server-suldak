package com.sulsul.suldaksuldak.repo.auth;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserDto> findByEmail(
            String email
    ) {
        return Optional.ofNullable(
                getUserDtoQuery()
                        .from(user)
                        .where(
                                emailEq(email)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public List<UserDto> findByEmailOrNickname(String email, String nickname) {
        return getUserDtoQuery()
                .from(user)
                .where(
                        user.email.eq(email)
                                .or(user.nickname.eq(nickname))
                )
                .fetch();
    }

    @Override
    public Optional<UserDto> loginUser(String email, String password) {
        return Optional.ofNullable(
                getUserDtoQuery()
                        .from(user)
                        .where(
                                emailEq(email),
                                passwordEq(password)
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
                                user.email,
                                user.password,
                                user.nickname,
                                user.gender,
                                user.birthdayYear,
                                user.registration,
                                user.createdAt,
                                user.modifiedAt
                        )
                );
    }

    private BooleanExpression emailEq(
            String email
    ) {
        return user.email.eq(email);
    }

    private BooleanExpression nicknameEq(
            String nickname
    ) {
        return user.nickname.eq(nickname);
    }

    private BooleanExpression passwordEq(
            String password
    ) {
        return user.password.eq(password);
    }
}
