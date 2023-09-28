package com.sulsul.suldaksuldak.repo.auth;

import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.dto.auth.UserDto;

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
}
