package com.sulsul.suldaksuldak.repo.auth;

import com.sulsul.suldaksuldak.dto.auth.UserDto;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserDto> findByUserEmail(
            String userId
    );
    Optional<UserDto> findByNickname(
            String nickname
    );
    Optional<UserDto> loginUser(
            String email,
            String password
    );
}
