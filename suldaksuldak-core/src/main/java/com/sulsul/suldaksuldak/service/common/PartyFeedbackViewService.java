package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.party.feedback.PartyFeedbackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.feedback.PartyFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyFeedbackViewService {
    private final PartyFeedbackRepository partyFeedbackRepository;

    /**
     * 모임 피드백 조회
     */
    public List<PartyFeedbackDto> getPartyFeedbackList() {
        try {
            return partyFeedbackRepository.findAll()
                    .stream()
                    .map(PartyFeedbackDto::of)
                    .toList();
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
