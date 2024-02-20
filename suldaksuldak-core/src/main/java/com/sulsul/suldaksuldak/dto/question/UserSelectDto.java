package com.sulsul.suldaksuldak.dto.question;

import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import com.sulsul.suldaksuldak.domain.question.UserSelect;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

@Value
public class UserSelectDto {
    String id;
    Integer liquorQuestionIndex;
    String liquorQuestionText;
    Long liquorQuestionId;
    Integer liquorAnswerIndex;
    String liquorAnswerText;
    Long liquorAnswerId;
    Long userId;
    String userNickname;
    String userFileNm;

    public UserSelect toEntity(
            LiquorQuestion liquorQuestion,
            LiquorAnswer liquorAnswer,
            User user
    ) {
        return UserSelect.of(
                id,
                liquorQuestion,
                liquorAnswer,
                user
        );
    }

    public static UserSelect updateEntity(
            UserSelect userSelect,
            LiquorAnswer liquorAnswer
    ) {
        if (!userSelect.getLiquorAnswer().equals(liquorAnswer))
            userSelect.setLiquorAnswer(liquorAnswer);
        return userSelect;
    }
}
