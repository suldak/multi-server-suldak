package com.sulsul.suldaksuldak.dto.search;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "추천 검색어 Request")
public class RecommendSearchTextReq {
    @ApiModelProperty(value = "기본키 (없으면 생성)")
    Long id;
    @ApiModelProperty(value = "검색어", required = true)
    String text;

    public RecommendSearchTextDto toDto() {
        return RecommendSearchTextDto.of(
                id,
                true,
                text
        );
    }

    public RecommendSearchTextDto toDto(
            Boolean isActive
    ) {
        return RecommendSearchTextDto.of(
                id,
                isActive,
                text
        );
    }
}
