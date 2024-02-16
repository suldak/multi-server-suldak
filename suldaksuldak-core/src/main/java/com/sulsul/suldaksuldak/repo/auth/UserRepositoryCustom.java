package com.sulsul.suldaksuldak.repo.auth;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.dto.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserDto> findByUserEmail(
            String userEmail
    );
    Optional<UserDto> findByNickname(
            String nickname
    );
    Optional<UserDto> findUserBySocial(
            String userEmail,
            String password,
            Registration registration
    );
    List<UserDto> findByOptions(
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
    );
}
