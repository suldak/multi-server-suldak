package com.sulsul.suldaksuldak.dto.admin.feedback;

import lombok.Value;

@Value
public class GroupUserFeedbackDto {
    Long targetUserPriKey;
    Long goodFeedbackCnt;
    Long badFeedbackCnt;
}
