package com.sulsul.suldaksuldak.dto.liquor.recipe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "레시피 정보")
public class LiquorRecipeRes {
    @ApiModelProperty(value = "레시피 기본키")
    Long id;
    @ApiModelProperty(value = "내용")
    String content;
    @ApiModelProperty(value = "해당되는 술의 기본키")
    Long liquorId;

    public static LiquorRecipeRes from (
            LiquorRecipeDto liquorRecipeDto
    ) {
        return new LiquorRecipeRes(
                liquorRecipeDto.getId(),
                liquorRecipeDto.getContent(),
                liquorRecipeDto.getLiquorId()
        );
    }
}
