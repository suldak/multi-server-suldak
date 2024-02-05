package com.sulsul.suldaksuldak.dto.party.guest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "모임 참가원 Response")
public class PartyGuestDto {
    @ApiModelProperty(value = "참가원 관리 Table 기본키")
    String id;
    @ApiModelProperty(value = "모임 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "모임 이름")
    String partyName;
    @ApiModelProperty(value = "참가원 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "참가원 닉네임")
    String userNickname;
    @ApiModelProperty(value = "확정 여부")
    Boolean isConfirm;
}
