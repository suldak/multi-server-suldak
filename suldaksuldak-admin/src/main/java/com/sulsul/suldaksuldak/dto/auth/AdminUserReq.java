package com.sulsul.suldaksuldak.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "관리자 유저 Req")
public class AdminUserReq {
    @ApiModelProperty(value = "관리자 기본키 (생량하면 생성)")
    Long priKey;
    @ApiModelProperty(value = "관리자 ID", required = true)
    String adminId;
    @ApiModelProperty(value = "관리자 PW", required = true)
    String adminPw;
    @ApiModelProperty(value = "관리자 이름", required = true)
    String adminNm;
}
