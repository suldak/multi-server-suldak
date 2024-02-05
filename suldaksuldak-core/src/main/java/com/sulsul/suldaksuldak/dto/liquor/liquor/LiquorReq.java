package com.sulsul.suldaksuldak.dto.liquor.liquor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

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
    @ApiModelProperty(value = "술 검색을 위한 문구들", required = true)
    String searchTag;
    @ApiModelProperty(value = "술의 레피시")
    String liquorRecipe;
    @ApiModelProperty(value = "술의 정확한 도수", required = true)
    Double detailAbv;
    @ApiModelProperty(value = "술의 자세한 설명", required = true)
    String detailExplanation;
    // -----------------------------------------------------------
    @ApiModelProperty(value = "술의 도수 기본키")
    Long liquorAbvId;
    @ApiModelProperty(value = "2차 분류 기본키")
    Long liquorDetailId;
    @ApiModelProperty(value = "좋아하는 정도 기본키")
    Long drinkingCapacityId;
    @ApiModelProperty(value = "1차 분류 기본키")
    Long liquorNameId;
    // -----------------------------------------------------------
    @ApiModelProperty(value = "추천 안주 기본키 리스트")
    List<Long> snackPriKeys;
    @ApiModelProperty(value = "판매처 기본키 리스트")
    List<Long> sellPriKeys;
    @ApiModelProperty(value = "재료 기본키 리스트")
    List<Long> materialPriKeys;
    @ApiModelProperty(value = "기분 기본키 리스트")
    List<Long> statePriKeys;
    @ApiModelProperty(value = "맛 기본키 리스트")
    List<Long> tastePriKeys;

    public LiquorDto toDto() {
        return LiquorDto.of(
                id,
                name,
                summaryExplanation,
                detailExplanation,
                searchTag,
                liquorRecipe,
                detailAbv,
                liquorAbvId,
                liquorDetailId,
                drinkingCapacityId,
                liquorNameId
        );
    }

    public LiquorTotalReq toTotalReq(
            Long priKey
    ) {
        return new LiquorTotalReq(
                priKey,
                searchTag,
                snackPriKeys,
                sellPriKeys,
                materialPriKeys,
                statePriKeys,
                tastePriKeys,
                null,
                null,
                null,
                null
        );
    }
}
