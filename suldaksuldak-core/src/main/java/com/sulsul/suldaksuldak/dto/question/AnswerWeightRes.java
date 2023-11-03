package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "프로필 답변에 대한 가중치 Res")
public class AnswerWeightRes {
    @ApiModelProperty(value = "답변 기본키")
    Long priKey;
    @ApiModelProperty(value = "태그 종류")
    TagType tagType;
    @ApiModelProperty(value = "해당 태그의 기본키")
    Long tagId;
    @ApiModelProperty(value = "가중치")
    Double weight;
    @ApiModelProperty(value = "답변의 기본키")
    Long liquorAnswerPriKey;

    public static AnswerWeightRes from (
            AnswerWeightDto answerWeightDto
    ) {
        return new AnswerWeightRes(
                answerWeightDto.getId(),
                answerWeightDto.getTagType(),
                answerWeightDto.getTagId(),
                answerWeightDto.getWeight(),
                answerWeightDto.getLiquorAnswerId()
        );
    }
}
