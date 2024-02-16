package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.PartyFeedback;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.feedback.PartyFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyFeedbackAdminService {
    private final PartyFeedbackRepository partyFeedbackRepository;

    /**
     * 모임 피드백 내용 생성 및 수정
     */
    public Boolean createPartyFeedback(
            Long id,
            String feedBackText,
            Double score
    ) {
        try {
            if (id == null) {
                partyFeedbackRepository.save(
                        PartyFeedback.of(
                                null,
                                feedBackText,
                                score
                        )
                );
            } else {
                partyFeedbackRepository.findById(id)
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (feedBackText != null)
                                        findEntity.setFeedBackText(feedBackText);
                                    if (score != null)
                                        findEntity.setScore(score);
                                    partyFeedbackRepository.save(findEntity);
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "해당 데이터를 찾을 수 없습니다."
                                    );
                                }
                        );
            }
            return true;
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

    /**
     * 모임 피드백 내용 삭제
     */
    public Boolean deletePartyFeedback(
            Long priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 NULL 입니다."
                );
            partyFeedbackRepository.deleteById(priKey);
            return true;
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
