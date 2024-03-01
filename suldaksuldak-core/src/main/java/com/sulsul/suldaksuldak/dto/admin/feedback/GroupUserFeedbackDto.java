package com.sulsul.suldaksuldak.dto.admin.feedback;

import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import lombok.Value;

@Value
public class GroupUserFeedbackDto {
    Long targetUserPriKey;
    FeedbackType feedbackType;
    Long count;
}
