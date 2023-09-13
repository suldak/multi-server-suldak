package com.sulsul.suldaksuldak.dto.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "상태 Request")
public class StateTypeDto {
    @ApiModelProperty(value = "상태 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "상태 이름", required = true, example = "기분 전환, 피곤할 때, ...")
    String name;
}
