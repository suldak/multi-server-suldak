package com.sulsul.suldaksuldak.dto.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "프로필 질문 Req")
public class LiquorQuestionReq {
    @ApiModelProperty(value = "질문 기본키 (없으면 생성)")
    Long priKey;
    @ApiModelProperty(value = "질문 순서", required = true)
    Integer qIndex;
    @ApiModelProperty(value = "내용", required = true)
    String qText;

    public LiquorQuestionDto toDto () {
        return LiquorQuestionDto.of(
                priKey,
                qIndex,
                qText
        );
    }
}
