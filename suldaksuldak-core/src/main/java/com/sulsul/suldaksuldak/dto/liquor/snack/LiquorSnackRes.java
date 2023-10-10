package com.sulsul.suldaksuldak.dto.liquor.snack;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

//@Value
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "안주 정보")
public class LiquorSnackRes {
    @ApiModelProperty(value = "안주 기본키")
    Long id;
    @ApiModelProperty(value = "안주 이름")
    String name;

    public static LiquorSnackRes from (
            LiquorSnackDto liquorSnackDto
    ) {
        return new LiquorSnackRes(
                liquorSnackDto.getId(),
                liquorSnackDto.getName()
        );
    }
}
