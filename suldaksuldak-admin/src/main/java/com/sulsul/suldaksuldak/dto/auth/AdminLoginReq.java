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
public class AdminLoginReq {
    @ApiModelProperty(value = "관리자 ID", required = true)
    String adminId;
    @ApiModelProperty(value = "관리자 PW", required = true)
    String adminPw;
}
