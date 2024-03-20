package com.sulsul.suldaksuldak.dto.user;

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
    Double level;
    Double warningCnt;
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

    public static UserDto idOnly(
            Long priKey
    ) {
        return new UserDto(
                priKey,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static UserDto of (
            Long id,
            String userEmail,
            String userPw,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration,
            Double level,
            Double warningCnt,
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
