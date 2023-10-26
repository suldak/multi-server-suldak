package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import lombok.Value;

@Value
public class LiquorAnswerDto {
    Long id;
    Integer aIndex;
    String aText;
    Long liquorQuestionId;

    public static LiquorAnswerDto of (
            Long id,
            Integer aIndex,
            String aText,
            Long liquorQuestionId
    ) {
        return new LiquorAnswerDto(
                id,
                aIndex,
                aText,
                liquorQuestionId
        );
    }

    public LiquorAnswer toEntity(
            LiquorQuestion liquorQuestion
    ) {
        return LiquorAnswer.of(
                id,
                aIndex,
                aText,
                liquorQuestion
        );
    }

    public LiquorAnswer updateEntity(
            LiquorAnswer liquorAnswer
    ) {
        if (aIndex != null) liquorAnswer.setAIndex(aIndex);
        if (aText != null) liquorAnswer.setAText(aText);
        return liquorAnswer;
    }
}
