package com.sulsul.suldaksuldak.dto.question;

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
@ApiModel(value = "유저의 프로필 질문의 답변 Req")
public class UserSelectReq {
    @ApiModelProperty(value = "유저 기본키", required = true)
    Long userPriKey;
    List<QuestionAnswerObj> questionAnswerMap;

    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "질문 / 답변 세트")
    public static class QuestionAnswerObj {
        @ApiModelProperty(value = "질문 기본키", required = true)
        Long questionPriKey;
        @ApiModelProperty(value = "답변 기본키 리스트", required = true)
        List<Long> answerPriKeyList;
    }
}
