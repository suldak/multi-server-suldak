package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {
    Long id;
    String userEmail;
    String userPw;
    String nickname;
    Gender gender;
    Integer birthdayYear;
    Registration registration;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static UserDto of (
            Long id,
            String userEmail,
            String userPw,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration
    ) {
        return new UserDto(
                id,
                userEmail,
                userPw,
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
                userEmail,
                userPw,
                nickname,
                gender,
                birthdayYear,
                registration
        );
    }

    public User updateEntity(
            User user
    ) {
        if (nickname != null) user.setNickname(nickname);
        return user;
    }
}
