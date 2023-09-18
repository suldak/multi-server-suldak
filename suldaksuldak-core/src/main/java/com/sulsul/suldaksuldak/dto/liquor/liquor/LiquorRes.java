package com.sulsul.suldaksuldak.dto.liquor.liquor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "술 Response")
public class LiquorRes {
    @ApiModelProperty(value = "술 기본키")
    Long id;
    @ApiModelProperty(value = "술 이름")
    String name;
    @ApiModelProperty(value = "술 요약 설명")
    String summaryExplanation;
    @ApiModelProperty(value = "술 상세 설명")
    String detailExplanation;
    @ApiModelProperty(value = "도수 기본키")
    Long liquorAbvId;
    @ApiModelProperty(value = "2차 분류 기본키")
    Long liquorDetailId;
    @ApiModelProperty(value = "추천 주량 기본키")
    Long drinkingCapacityId;
    @ApiModelProperty(value = "생성 일시")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "수정 일시")
    LocalDateTime modifiedAt;

    public static LiquorRes from (LiquorDto liquorDto) {
        return new LiquorRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                liquorDto.getLiquorAbvId(),
                liquorDto.getLiquorDetailId(),
                liquorDto.getDrinkingCapacityId(),
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }
}
