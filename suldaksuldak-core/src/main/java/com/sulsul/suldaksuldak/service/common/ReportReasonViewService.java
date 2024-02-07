package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyReasonDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.report.reason.party.ReportPartyReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportReasonViewService {
    private final ReportPartyReasonRepository reportPartyReasonRepository;

    public List<ReportPartyReasonDto> getReportPartyReasonList() {
        try {
            return reportPartyReasonRepository.findAll()
                    .stream()
                    .map(ReportPartyReasonDto::of)
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
