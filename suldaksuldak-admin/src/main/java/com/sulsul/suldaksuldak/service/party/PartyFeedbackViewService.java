package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.feedback.UserPartyFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyFeedbackViewService {
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;

    public List<UserPartyFeedbackDto> getUserPartyFeedbackDtoList(
            FeedbackType feedbackType,
            String comment,
            Long partyPriKey,
            Long writerPriKey,
            Long targetUserPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        try {
            return userPartyFeedbackRepository.findByOption(
                    feedbackType,
                    comment,
                    partyPriKey,
                    writerPriKey,
                    targetUserPriKey,
                    startAt,
                    endAt
            );
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }
}
