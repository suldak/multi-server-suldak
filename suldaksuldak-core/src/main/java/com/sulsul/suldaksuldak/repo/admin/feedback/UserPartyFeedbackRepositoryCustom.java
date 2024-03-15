package com.sulsul.suldaksuldak.repo.admin.feedback;

import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.dto.admin.feedback.GroupUserFeedbackDto;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserPartyFeedbackRepositoryCustom {
    List<UserPartyFeedbackDto> findByOption(
            FeedbackType feedbackType,
            String comment,
            Long partyPriKey,
            Long writerPriKey,
            Long targetUserPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt
    );

    List<GroupUserFeedbackDto> findGroupDtoByTargetPriKey(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime
    );
}
