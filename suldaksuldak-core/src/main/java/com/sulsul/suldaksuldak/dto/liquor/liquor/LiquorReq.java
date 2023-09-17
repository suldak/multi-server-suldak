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
    @ApiModelProperty(value = "술의 도수 기본키")
    Long liquorAbvId;
    @ApiModelProperty(value = "2차 분류 기본키")
    Long liquorDetailId;
    @ApiModelProperty(value = "좋아하는 정도 기본키")
    Long drinkingCapacityId;
    @ApiModelProperty(value = "1차 분류 기본키")
    Long liquorNameId;

    public LiquorDto toDto() {
        return LiquorDto.of(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                liquorAbvId,
                liquorDetailId,
                drinkingCapacityId,
                liquorNameId
        );
    }
}
