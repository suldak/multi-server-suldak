package com.sulsul.suldaksuldak.dto.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "프로필 질문의 답변 Req")
public class LiquorAnswerRes {
    @ApiModelProperty(value = "답변 기본키")
    Long priKey;
    @ApiModelProperty(value = "답변 순서")
    Integer aIndex;
    @ApiModelProperty(value = "내용")
    String aText;
    @ApiModelProperty(value = "질문의 기본키")
    Long questionPriKey;

    public static LiquorAnswerRes from (
            LiquorAnswerDto liquorAnswerDto
    ) {
        return new LiquorAnswerRes(
                liquorAnswerDto.getId(),
                liquorAnswerDto.getAIndex(),
                liquorAnswerDto.getAText(),
                liquorAnswerDto.getLiquorQuestionId()
        );
    }
}
