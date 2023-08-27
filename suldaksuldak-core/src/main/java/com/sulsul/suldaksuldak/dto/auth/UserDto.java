package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserDto {
    Long id;
    String email;
    String password;
    String nickname;
    Gender gender;
    Integer birthdayYear;
    Registration registration;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static UserDto of (
            Long id,
            String email,
            String password,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration
    ) {
        return new UserDto(
                id,
                email,
                password,
                nickname,
                gender,
                birthdayYear,
                registration,
                null,
                null
        );
    }

    public User toEntity() {
        return User.of(
                id,
                email,
                password,
                nickname,
                gender,
                birthdayYear,
                registration
        );
    }

    public User updateEntity(
            User user
    ) {
        // setter
        return user;
    }
}
