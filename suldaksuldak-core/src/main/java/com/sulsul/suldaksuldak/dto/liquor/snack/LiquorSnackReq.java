package com.sulsul.suldaksuldak.dto.liquor.snack;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "추천 안주 Request")
public class LiquorSnackReq {
    @ApiModelProperty(value = "추천 안주 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "추천 안주 이름", required = true)
    String name;

    public LiquorSnackDto toDto() {
        return LiquorSnackDto.of(
                id,
                name
        );
    }
}
