package com.sulsul.suldaksuldak.dto.admin.feedback;

import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserPartyFeedbackDto {
    Long id;
    FeedbackType feedbackType;
    String comment;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    Long writerPriKey;
    String writerNickname;
    String writerFileNm;
    Long targetUserPriKey;
    String targetUserNickname;
    String targetUserFileNm;
    LocalDateTime feedbackAt;
}
