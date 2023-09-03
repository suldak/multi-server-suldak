package com.sulsul.suldaksuldak.repo.auth;

import com.sulsul.suldaksuldak.dto.auth.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserDto> findByEmail(
            String email
    );
    List<UserDto> findByEmailOrNickname(
            String email,
            String nickname
    );
    Optional<UserDto> loginUser(
            String email,
            String password
    );
}
