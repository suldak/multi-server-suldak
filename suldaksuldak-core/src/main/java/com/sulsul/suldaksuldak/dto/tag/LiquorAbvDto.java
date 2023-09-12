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
@ApiModel(value = "도수 Request")
public class LiquorAbvDto {
    @ApiModelProperty(value = "도수 TAG 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "도수 정도", required = true, example = "~5% / 6 ~ 10% ...")
    String name;
}
