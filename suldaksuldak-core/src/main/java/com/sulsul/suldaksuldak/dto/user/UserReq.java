package com.sulsul.suldaksuldak.dto.user;


import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User Request")
public class UserReq {
    @ApiModelProperty(value = "유저 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "이메일 (Id)", required = true)
    String userEmail;
    @ApiModelProperty(value = "비밀번호 (소셜 회원가입일 경우 identity 값을 넣어야 함", required = true)
    String userPw;
    @ApiModelProperty(value = "닉네임", required = true)
    String nickname;
    @ApiModelProperty(value = "성별", required = true)
    Gender gender;
    @ApiModelProperty(value = "출생 년도", required = true)
    Integer birthdayYear;
    @ApiModelProperty(value = "가입 방법", required = true)
    Registration registration;
    @ApiModelProperty(value = "유저 레빌")
    Double level;
    @ApiModelProperty(value = "유저 신고 누적")
    Integer warningCnt;
    @ApiModelProperty(value = "유저 활성화 여부")
    Boolean isActive;
    @ApiModelProperty(value = "자기소개")
    String selfIntroduction;
    @ApiModelProperty(value = "알림 여부")
    Boolean alarmActive;
    @ApiModelProperty(value = "소리 알림 여부")
    Boolean soundActive;
    @ApiModelProperty(value = "진동 알림 여부")
    Boolean vibrationActive;
    @ApiModelProperty(value = "앱 푸시 알림 여부")
    Boolean pushActive;
    @ApiModelProperty(value = "마케팅 정보 알림 여부")
    Boolean marketingActive;

    public static UserDto toDto(
            Long userPriKey,
            String nickname,
            String selfIntroduction,
            Double level,
            Integer warningCnt,
            Boolean isActive
    ) {
        return UserDto.of(
                userPriKey,
                null,
                null,
                nickname,
                null,
                null,
                null,
                level,
                warningCnt,
                isActive,
                selfIntroduction,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public UserDto toDto() {
        try {
            return UserDto.of(
                    id,
                    userEmail,
                    UtilTool.encryptPassword(userPw, userEmail),
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
                    null
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    public UserDto createUser() {
        try {
            return UserDto.of(
                    id,
                    userEmail,
                    UtilTool.encryptPassword(userPw, userEmail),
                    nickname,
                    gender,
                    birthdayYear,
                    registration,
                    25.0,
                    0,
                    true,
                    selfIntroduction,
                    true,
                    true,
                    true,
                    true,
                    true,
                    null
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
