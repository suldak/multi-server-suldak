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

    public static User updateUserSimple(
            User user,
            String nickname,
            String selfIntroduction
    ) {
        if (nickname != null) user.setNickname(nickname);
        if (selfIntroduction != null && !selfIntroduction.isBlank()) user.setSelfIntroduction(selfIntroduction);
        return user;
    }

    /**
     * 유저 레벨 수정
     */
    public static User updateUserLevel(
            User user,
            Integer level
    ) {
        user.setLevel(level);
        return user;
    }

    /**
     * 유저 신고 누적 신고
     */
    public static User updateUserWarningCnt(
            User user,
            Integer warningCnt
    ) {
        user.setWarningCnt(warningCnt);
        return user;
    }

    /**
     * 유처 탈퇴 여부 수정
     */
    public static User updateUserActive(
            User user,
            Boolean isActive
    ) {
        user.setIsActive(isActive);
        return user;
    }

    /**
     * 수정 가능한 모든 정보 수정
     */
    public User updateUser(
            User user
    ) {
        if (nickname != null) user.setNickname(nickname);
        if (selfIntroduction != null && !selfIntroduction.isBlank()) user.setSelfIntroduction(selfIntroduction);
        if (level != null) user.setLevel(level);
        if (warningCnt != null) user.setWarningCnt(warningCnt);
        if (isActive != null) user.setIsActive(isActive);
        return user;
    }
}
