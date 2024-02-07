package com.sulsul.suldaksuldak.service.report;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.report.ReportPartyReason;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.report.reason.party.ReportPartyReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportReasonService {
    private final ReportPartyReasonRepository reportPartyReasonRepository;

    /**
     * 모임 신고 이유 생성 및 수정
     */
    public Boolean createReportPartyReason(
            Long id,
            String reason
    ) {
        try {
            if (id == null) {
                reportPartyReasonRepository.save(
                        ReportPartyReason.of(
                                null,
                                reason
                        )
                );
            } else {
                reportPartyReasonRepository.findById(id)
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (reason != null)
                                        findEntity.setReason(reason);
                                    reportPartyReasonRepository.save(findEntity);
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "해당 모임 신고 이유를 찾을 수 없습니다."
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
     * 모임 신고 이유 삭제
     */
    public Boolean deleteReportPartyReason (
            Long id
    ) {
        try {
            if (id == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            reportPartyReasonRepository.deleteById(id);
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
