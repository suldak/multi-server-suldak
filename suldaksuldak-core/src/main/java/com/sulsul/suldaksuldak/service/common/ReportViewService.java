package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentDto;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.report.party.ReportPartyRepository;
import com.sulsul.suldaksuldak.repo.report.party.comment.ReportPartyCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportViewService {
    private final ReportPartyRepository reportPartyRepository;
    private final ReportPartyCommentRepository reportPartyCommentRepository;

    /**
     * 모임 신고 목록 조회
     */
    public List<ReportPartyDto> getReportPartyList(
            Long userPriKey,
            Long partyPriKey,
            Long reportReasonPriKey,
            Boolean complete
    ) {
        try {
            return reportPartyRepository.findByOption(
                    userPriKey,
                    partyPriKey,
                    reportReasonPriKey,
                    complete
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

    public List<ReportPartyCommentDto> getReportPartyCommentList(
            Long userPriKey,
            Long partyPriKey
    ) {
        try {
            return reportPartyCommentRepository.findByOptions(
                    userPriKey,
                    partyPriKey
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
