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
@ApiModel(value = "프로필 질문의 답변 Req")
public class LiquorAnswerReq {
    @ApiModelProperty(value = "답변 기본키 (없으면 생성)")
    Long priKey;
    @ApiModelProperty(value = "답변 순서", required = true)
    Integer aIndex;
    @ApiModelProperty(value = "내용", required = true)
    String aText;
    @ApiModelProperty(value = "질문의 기본키", required = true)
    Long questionPriKey;

    public LiquorAnswerDto toDto() {
        return LiquorAnswerDto.of(
                priKey,
                aIndex,
                aText,
                questionPriKey
        );
    }
}
