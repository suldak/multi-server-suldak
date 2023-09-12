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
@ApiModel(value = "재료 Request")
public class LiquorMaterialDto {
    @ApiModelProperty(value = "재료 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "재료 이름", required = true)
    String name;
}
