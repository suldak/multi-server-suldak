package com.sulsul.suldaksuldak.dto.cut;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "차단 유저 Request")
public class CutOffUserReq {
    @ApiModelProperty(value = "해당 유저의 기본키")
    Long userId;
    @ApiModelProperty(value = "차단 할 (당한) 유저의 기본키")
    Long cutUserId;
}
