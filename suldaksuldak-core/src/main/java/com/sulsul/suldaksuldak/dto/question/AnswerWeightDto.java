package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.domain.question.AnswerWeight;
import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import lombok.Value;

@Value
public class AnswerWeightDto {
    Long id;
    TagType tagType;
    Long tagId;
    Double weight;
    Long liquorAnswerId;

    public static AnswerWeightDto of (
            Long id,
            TagType tagType,
            Long tagId,
            Double weight,
            Long liquorAnswerId
    ) {
        return new AnswerWeightDto(
                id,
                tagType,
                tagId,
                weight,
                liquorAnswerId
        );
    }

    public AnswerWeight toEntity(
            LiquorAnswer liquorAnswer
    ) {
        return AnswerWeight.of(
                id,
                tagType,
                tagId,
                weight,
                liquorAnswer
        );
    }

    public AnswerWeight updateEntity(
            AnswerWeight answerWeight
    ) {
        if (weight != null) answerWeight.setWeight(weight);
        return answerWeight;
    }
}
