package com.sulsul.suldaksuldak.dto.liquor.recipe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "술 레시피 Request")
public class LiquorRecipeReq {
    @ApiModelProperty(value = "술 레시피 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "레시피 내용", required = true)
    String content;
    @ApiModelProperty(value = "술 DB의 기본키", required = true)
    Long liquorId;

    public LiquorRecipeDto toDto() {
        return LiquorRecipeDto.of(
                id,
                content,
                liquorId
        );
    }
}
