package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "프로필 답변에 대한 가중치 Req")
public class AnswerWeightReq {
    @ApiModelProperty(value = "답변 기본키 (없으면 생성)")
    Long priKey;
    @ApiModelProperty(value = "태그 종류", required = true)
    TagType tagType;
    @ApiModelProperty(value = "해당 태그의 기본키", required = true)
    Long tagId;
    @ApiModelProperty(value = "가중치", required = true)
    Double weight;
    @ApiModelProperty(value = "답변의 기본키", required = true)
    Long liquorAnswerPriKey;

    public AnswerWeightDto toDto() {
        return AnswerWeightDto.of(
                priKey,
                tagType,
                tagId,
                weight,
                liquorAnswerPriKey
        );
    }
}
