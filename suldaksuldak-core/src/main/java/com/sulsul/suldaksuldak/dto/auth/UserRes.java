package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserRes {
    Long id;
    String email;
    String nickname;
    Gender gender;
    Integer birthdayYear;
    Registration registration;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    String refreshToken;

    public static UserRes from (UserDto userDto) {
        return new UserRes(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getNickname(),
                userDto.getGender(),
                userDto.getBirthdayYear(),
                userDto.getRegistration(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                null
        );
    }

    public static UserRes from (
            UserDto userDto,
            TokenRes tokenRes
    ) {
        return new UserRes(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getNickname(),
                userDto.getGender(),
                userDto.getBirthdayYear(),
                userDto.getRegistration(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                tokenRes.getRefreshToken()
        );
    }
}
