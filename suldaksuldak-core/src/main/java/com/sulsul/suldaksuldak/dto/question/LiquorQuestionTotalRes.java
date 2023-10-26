package com.sulsul.suldaksuldak.dto.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.List;

@Value
@ApiModel(value = "프로필 질문 / 답변 Response")
public class LiquorQuestionTotalRes {
    @ApiModelProperty(value = "질문 기본키")
    Long questionPriKey;
    @ApiModelProperty(value = "질문 순서")
    Integer qIndex;
    @ApiModelProperty(value = "질문 내용")
    String qText;
    @ApiModelProperty(value = "질문에 답변 목록")
    List<LiquorAnswerRes> liquorAnswerRes;

    public static LiquorQuestionTotalRes of (
            LiquorQuestionDto liquorQuestionDto,
            List<LiquorAnswerDto> liquorAnswerRes
    ) {
        return new LiquorQuestionTotalRes(
                liquorQuestionDto.getId(),
                liquorQuestionDto.getQIndex(),
                liquorQuestionDto.getQText(),
                liquorAnswerRes
                        .stream()
                        .map(LiquorAnswerRes::from)
                        .toList()
        );
    }
}
