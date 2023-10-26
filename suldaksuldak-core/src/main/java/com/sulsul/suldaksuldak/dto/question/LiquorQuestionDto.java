package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import lombok.Value;

@Value
public class LiquorQuestionDto {
    Long id;
    Integer qIndex;
    String qText;

    public static LiquorQuestionDto of (
            Long id,
            Integer qIndex,
            String qText
    ) {
        return new LiquorQuestionDto(
                id,
                qIndex,
                qText
        );
    }

    public LiquorQuestion toEntity() {
        return LiquorQuestion.of(
                id,
                qIndex,
                qText
        );
    }

    public LiquorQuestion updateEntity(
            LiquorQuestion liquorQuestion
    ) {
        if (qIndex != null) liquorQuestion.setQIndex(qIndex);
        if (qText != null) liquorQuestion.setQText(qText);
        return liquorQuestion;
    }
}
