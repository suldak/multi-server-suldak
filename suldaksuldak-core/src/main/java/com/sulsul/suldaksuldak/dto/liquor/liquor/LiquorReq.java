package com.sulsul.suldaksuldak.dto.liquor.liquor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "술 Request")
public class LiquorReq {
    @ApiModelProperty(value = "술 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "술 이름", required = true)
    String name;
    @ApiModelProperty(value = "술의 요약 설명", required = true)
    String summaryExplanation;
    @ApiModelProperty(value = "술의 자세한 설명", required = true)
    String detailExplanation;

    public LiquorDto toDto() {
        return LiquorDto.of(
                id,
                name,
                summaryExplanation,
                detailExplanation
        );
    }
}
