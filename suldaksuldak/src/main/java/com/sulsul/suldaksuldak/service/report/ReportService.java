package com.sulsul.suldaksuldak.service.report;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.report.ReportParty;
import com.sulsul.suldaksuldak.domain.report.ReportPartyReason;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.report.party.ReportPartyRepository;
import com.sulsul.suldaksuldak.repo.report.reason.party.ReportPartyReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final ReportPartyReasonRepository reportPartyReasonRepository;
    private final ReportPartyRepository reportPartyRepository;

    /**
     * 모임 신고 하기
     */
    public Boolean createReportParty(
            Long userPriKey,
            Long partyPriKey,
            Long reasonPriKey
    ) {
        try {
            if (
                    userPriKey == null ||
                    partyPriKey == null ||
                    reasonPriKey == null
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "유저 정보를 찾을 수 없습니다."
                );
            Optional<Party> party = partyRepository.findById(partyPriKey);
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "모임 정보를 찾을 수 없습니다."
                );
            Optional<ReportPartyReason> reportPartyReason =
                    reportPartyReasonRepository.findById(reasonPriKey);
            if (reportPartyReason.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "모임 신고 이유를 찾을 수 없습니다."
                );
            reportPartyRepository.save(
                    ReportParty.of(
                            null,
                            reportPartyReason.get(),
                            user.get(),
                            party.get(),
                            false
                    )
            );
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
