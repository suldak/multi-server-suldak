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
    Integer level;
    Integer warningCnt;
    Boolean isActive;
    String selfIntroduction;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static UserDto of (
            Long id,
            String userEmail,
            String userPw,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration,
            Integer level,
            Integer warningCnt,
            Boolean isActive,
            String selfIntroduction
    ) {
        return new UserDto(
                id,
                userEmail,
                userPw,
                nickname,
                gender,
                birthdayYear,
                registration,
                level,
                warningCnt,
                isActive,
                selfIntroduction,
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
                registration,
                level,
                warningCnt,
                isActive,
                selfIntroduction
        );
    }

    public User updateEntity(
            User user
    ) {
        if (nickname != null) user.setNickname(nickname);
        return user;
    }
}
