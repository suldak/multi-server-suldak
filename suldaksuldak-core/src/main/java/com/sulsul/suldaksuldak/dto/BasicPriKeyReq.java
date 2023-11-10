package com.sulsul.suldaksuldak.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "기본키 Req")
public class BasicPriKeyReq {
    @ApiModelProperty(value = "해당 정보의 기본키", required = true)
    Long priKey;
}
