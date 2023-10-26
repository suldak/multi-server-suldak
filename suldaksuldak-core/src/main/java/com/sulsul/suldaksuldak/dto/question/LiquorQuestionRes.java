package com.sulsul.suldaksuldak.dto.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "프로필 질문 Response")
public class LiquorQuestionRes {
    @ApiModelProperty(value = "질문 기본키")
    Long priKey;
    @ApiModelProperty(value = "질문 순서")
    Integer qIndex;
    @ApiModelProperty(value = "질문 내용")
    String qText;

    public static LiquorQuestionRes from (
            LiquorQuestionDto liquorQuestionDto
    ) {
        return new LiquorQuestionRes(
                liquorQuestionDto.getId(),
                liquorQuestionDto.getQIndex(),
                liquorQuestionDto.getQText()
        );
    }
}
