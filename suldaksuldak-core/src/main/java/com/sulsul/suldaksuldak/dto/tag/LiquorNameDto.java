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
@ApiModel(value = "1차 분류 Request")
public class LiquorNameDto {
    @ApiModelProperty(value = "1차 분류 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "1차 분류 이름", required = true)
    String name;
}
