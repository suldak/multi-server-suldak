package com.sulsul.suldaksuldak.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "User Response")
public class UserRes {
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
    @ApiModelProperty(value = "생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "수정 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime modifiedAt;
    @ApiModelProperty(value = "소셜 로그인 비밀번호 생성을 위한 identity 값")
    String identity;
    @ApiModelProperty(value = "Refresh Token")
    String refreshToken;

    public static UserRes from (UserDto userDto) {
        return new UserRes(
                userDto.getId(),
                userDto.getUserEmail(),
                userDto.getNickname(),
                userDto.getGender(),
                userDto.getBirthdayYear(),
                userDto.getRegistration(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                null,
                null
        );
    }

    public static UserRes from (
            UserDto userDto,
            TokenRes tokenRes
    ) {
        return new UserRes(
                userDto.getId(),
                userDto.getUserEmail(),
                userDto.getNickname(),
                userDto.getGender(),
                userDto.getBirthdayYear(),
                userDto.getRegistration(),
                userDto.getCreatedAt(),
                userDto.getModifiedAt(),
                null,
                tokenRes.getRefreshToken()
        );
    }

    public static UserRes from(
            SocialUserDto socialUserDto
    ) {
        return new UserRes(
                null,
                socialUserDto.getUserEmail(),
                socialUserDto.getNickname(),
                null,
                null,
                socialUserDto.getRegistration(),
                null,
                null,
                socialUserDto.getIdentity(),
                null
        );
    }
}
