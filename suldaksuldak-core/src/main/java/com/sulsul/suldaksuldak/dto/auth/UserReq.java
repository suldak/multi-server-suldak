package com.sulsul.suldaksuldak.dto.auth;


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
    @ApiModelProperty(value = "이메일 (Id)")
    String userEmail;
    @ApiModelProperty(value = "비밀번호")
    String userPw;
    @ApiModelProperty(value = "닉네임")
    String nickname;
    @ApiModelProperty(value = "성별")
    Gender gender;
    @ApiModelProperty(value = "출생 년도")
    Integer birthdayYear;
    @ApiModelProperty(value = "가입 방법")
    Registration registration;
    @ApiModelProperty(value = "카카오 / 네이버 소셜 로그인 때 받은 code")
    String code;
    @ApiModelProperty(value = "카카오 / 네이버 소셜 로그인 때 받은 state")
    String state;

    public UserDto toDto() {
        try {
            return UserDto.of(
                    id,
                    userEmail,
                    UtilTool.encryptPassword(userPw, userEmail),
                    nickname,
                    gender,
                    birthdayYear,
                    registration
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
