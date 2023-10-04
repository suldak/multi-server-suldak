package com.sulsul.suldaksuldak.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "로그인 Req")
public class LoginReq {
    @ApiModelProperty(value = "이메일 (Id)", required = true)
    String userEmail;
    @ApiModelProperty(value = "비밀번호 (소셜 회원가입일 경우 identity 값을 넣어야 함", required = true)
    String userPw;
}
