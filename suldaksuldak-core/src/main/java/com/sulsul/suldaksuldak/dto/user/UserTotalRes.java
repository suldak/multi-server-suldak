package com.sulsul.suldaksuldak.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.dto.question.UserSelectDto;
import com.sulsul.suldaksuldak.dto.question.UserSelectRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@ApiModel(value = "User Total Response")
public class UserTotalRes {
    @ApiModelProperty(value = "유저 기본키")
    Long id;
    @ApiModelProperty(value = "Email")
    String userEmail;
    @ApiModelProperty(value = "닉네임")
    String nickname;
    @ApiModelProperty(value = "성별")
    Gender gender;
    @ApiModelProperty(value = "출생 년도")
    Integer birthdayYear;
    @ApiModelProperty(value = "가입 방법")
    Registration registration;
    @ApiModelProperty(value = "유저 레벨")
    Double level;
    @ApiModelProperty(value = "신고 누적")
    Double warningCnt;
    @ApiModelProperty(value = "활성화 여부")
    Boolean isActive;
    @ApiModelProperty(value = "자기소개")
    String selfIntroduction;
    @ApiModelProperty(value = "유저 사진 URL")
    String pictureUrl;
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
    @ApiModelProperty(value = "생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "수정 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime modifiedAt;
    UserSelectRes userSelect;

    public static UserTotalRes from(
            UserDto userDto,
            List<UserSelectDto> userSelectDtos
    ) {
        return new UserTotalRes(
                userDto.getId(),
                userDto.getUserEmail(),
                userDto.getNickname(),
                userDto.getGender(),
                userDto.getBirthdayYear(),
                userDto.getRegistration(),
                userDto.getLevel(),
                userDto.getWarningCnt(),
                userDto.getIsActive(),
                userDto.getSelfIntroduction(),
                userDto.getFileBaseNm() == null || userDto.getFileBaseNm().isBlank() ?
                        null : FileUrl.FILE_DOWN_URL.getUrl() + userDto.getFileBaseNm(),
                userDto.getAlarmActive(),
                userDto.getSoundActive(),
                userDto.getVibrationActive(),
                userDto.getPushActive(),
                userDto.getMarketingActive(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                UserSelectRes.from(userSelectDtos)
        );
    }
}
