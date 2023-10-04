package com.sulsul.suldaksuldak.dto.auth;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.file.FileDto;
import com.sulsul.suldaksuldak.tool.FileTool;
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
    String fileBaseNm;
    Boolean alarmActive;
    Boolean soundActive;
    Boolean vibrationActive;
    Boolean pushActive;
    Boolean marketingActive;
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
            String selfIntroduction,
            Boolean alarmActive,
            Boolean soundActive,
            Boolean vibrationActive,
            Boolean pushActive,
            Boolean marketingActive,
            String fileBaseNm
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
                fileBaseNm,
                alarmActive,
                soundActive,
                vibrationActive,
                pushActive,
                marketingActive,
                null,
                null
        );
    }

    public User toEntity(
            FileBase fileBase
    ) {
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
                selfIntroduction,
                alarmActive,
                soundActive,
                vibrationActive,
                pushActive,
                marketingActive,
                fileBase
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
     * 유저의 사진 수정
     */
    public static User updatePicture(
            User user,
            FileBase fileBase
    ) {
        if (user.getFileBase() != null) {
            FileTool.deleteFile(FileDto.of(fileBase));
        }
        user.setFileBase(fileBase);
        return user;
    }

    public User updateAlarmActive(
            User user
    ) {
        user.setAlarmActive(alarmActive);
        user.setSoundActive(soundActive);
        user.setVibrationActive(vibrationActive);
        user.setPushActive(pushActive);
        user.setMarketingActive(marketingActive);
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
