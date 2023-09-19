package com.sulsul.suldaksuldak.dto.bridge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "술과 Tag Request")
public class BridgeReq {
    @ApiModelProperty(value = "술 기본키", required = true)
    Long liquorPriKey;
    @ApiModelProperty(value = "태그 기본키 리스트", required = true)
    List<Long> tagPriKeys;
}
