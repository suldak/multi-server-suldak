package com.sulsul.suldaksuldak.dto.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "추천 검색어 Response")
public class RecommendSearchTextRes {
    @ApiModelProperty(value = "추천 검색어 기본키")
    Long id;
    @ApiModelProperty(value = "활성화 여부")
    Boolean isActive;
    @ApiModelProperty(value = "검색어")
    String text;

    public static RecommendSearchTextRes from(
            RecommendSearchTextDto dto
    ) {
        return new RecommendSearchTextRes(
                dto.getId(),
                dto.getIsActive(),
                dto.getText()
        );
    }
}
