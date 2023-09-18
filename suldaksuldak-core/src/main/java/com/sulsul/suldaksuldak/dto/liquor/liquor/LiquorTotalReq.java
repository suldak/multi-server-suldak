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
@ApiModel(value = "종합 술 Request")
public class LiquorTotalReq {
    @ApiModelProperty(value = "술 기본키, 저장 시 필수 / 태그 별 조회 시 필요 없음")
    Long id;
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

    @ApiModelProperty(value = "도수 기본키")
    Long liquorAbvId;
    @ApiModelProperty(value = "2차 분류 기본키")
    Long liquorDetailId;
    @ApiModelProperty(value = "주량 기본키")
    Long drinkingCapacityId;
    @ApiModelProperty(value = "1차 분류 기본키")
    Long liquorNameId;
}
