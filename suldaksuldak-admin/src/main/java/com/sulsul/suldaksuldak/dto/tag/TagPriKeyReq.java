package com.sulsul.suldaksuldak.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "태그 기본키 Req")
public class TagPriKeyReq {
    @ApiModelProperty(value = "태그의 기본키", required = true)
    Long priKey;
}
