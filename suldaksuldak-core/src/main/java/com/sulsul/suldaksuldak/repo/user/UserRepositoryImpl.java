package com.sulsul.suldaksuldak.repo.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
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

    @Override
    public List<UserDto> findByOptions(
            String userEmail,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Integer startYear,
            Integer endYear,
            Registration registration,
            List<Integer> levelList,
            List<Integer> warningCntList,
            Boolean isActive
    ) {
        return getUserDtoQuery()
                .from(user)
                .where(
                        userEmailLike(userEmail),
                        nicknameLike(nickname),
                        genderEq(gender),
                        birthdayYearEq(birthdayYear),
                        birthdayYearBtw(startYear, endYear),
                        registrationEq(registration),
                        levelListIn(levelList),
                        warningCntListIn(warningCntList),
                        isActiveEq(isActive)
                )
                .orderBy(user.id.asc())
                .fetch();
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
                                user.fileBase.fileNm,
                                user.alarmActive,
                                user.soundActive,
                                user.vibrationActive,
                                user.pushActive,
                                user.marketingActive,
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

    private BooleanExpression userEmailLike(
            String userEmail
    ) {
        return StringUtils.hasText(userEmail) ?
                user.userEmail.contains(userEmail) : null;
    }

    private BooleanExpression nicknameLike(
            String nickname
    ) {
        return StringUtils.hasText(nickname) ?
                user.nickname.contains(nickname) : null;
    }

    private BooleanExpression genderEq(
            Gender gender
    ) {
        return gender == null ? null :
                user.gender.eq(gender);
    }

    private BooleanExpression birthdayYearEq(
            Integer birthdayYear
    ) {
        return birthdayYear == null || birthdayYear <= 0 ?
                null : user.birthdayYear.eq(birthdayYear);
    }

    private BooleanExpression birthdayYearBtw(
            Integer startYear,
            Integer endYear
    ) {
        if (startYear == null || endYear == null || startYear <= 0 || endYear <= 0)
            return null;
        return user.birthdayYear.between(startYear, endYear);
    }

    private BooleanExpression registrationEq(
            Registration registration
    ) {
        return registration == null ? null :
                user.registration.eq(registration);
    }

    private BooleanExpression levelListIn(
            List<Integer> levelList
    ) {
        return levelList == null || levelList.isEmpty() ?
                null : user.level.in(levelList);
    }

    private BooleanExpression warningCntListIn(
            List<Integer> warningCntList
    ) {
        return warningCntList == null || warningCntList.isEmpty() ?
                null : user.warningCnt.in(warningCntList);
    }

    private BooleanExpression isActiveEq(
            Boolean isActive
    ) {
        return isActive == null ? null : user.isActive.eq(isActive);
    }
}