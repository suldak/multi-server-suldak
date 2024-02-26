package com.sulsul.suldaksuldak.service.report;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.domain.report.ReportParty;
import com.sulsul.suldaksuldak.domain.report.ReportPartyComment;
import com.sulsul.suldaksuldak.domain.report.ReportPartyReason;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentDto;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.comment.PartyCommentRepository;
import com.sulsul.suldaksuldak.repo.report.party.ReportPartyRepository;
import com.sulsul.suldaksuldak.repo.report.party.comment.ReportPartyCommentRepository;
import com.sulsul.suldaksuldak.repo.report.reason.party.ReportPartyReasonRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final CheckPriKeyService checkPriKeyService;
    private final PartyCommentRepository partyCommentRepository;
    private final ReportPartyReasonRepository reportPartyReasonRepository;
    private final ReportPartyRepository reportPartyRepository;
    private final ReportPartyCommentRepository reportPartyCommentRepository;

    /**
     * 모임 신고 하기
     */
    public Boolean createReportParty(
            Long userPriKey,
            Long partyPriKey,
            Long reasonPriKey
    ) {
        try {
            if (reasonPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "신고 이유에 대한 기본키가 누락되었습니다."
                );
            User user = checkPriKeyService.checkAndGetUser(userPriKey);
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            Optional<ReportPartyReason> reportPartyReason =
                    reportPartyReasonRepository.findById(reasonPriKey);
            if (reportPartyReason.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "모임 신고 이유를 찾을 수 없습니다."
                );
            List<ReportPartyDto> reportPartyDtos =
                    reportPartyRepository.findByOption(
                            userPriKey,
                            partyPriKey,
                            null,
                            null
                    );
            if (!reportPartyDtos.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 신고한 모임 입니다."
                );
            reportPartyRepository.save(
                    ReportParty.of(
                            null,
                            reportPartyReason.get(),
                            user,
                            party,
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

    public Boolean createReportPartyComment(
            Long userPriKey,
            String commentPriKey
    ) {
        try {
            if (commentPriKey == null || commentPriKey.isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "댓글 기본키가 누락되었습니다."
                );
            User user = checkPriKeyService.checkAndGetUser(userPriKey);
            Optional<PartyComment> partyComment =
                    partyCommentRepository.findById(commentPriKey);
            if (partyComment.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "댓글 정보를 찾을 수 없습니다."
                );
            if (user.getId().equals(partyComment.get().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신의 댓글은 신고할 수 없습니다."
                );
            Optional<ReportPartyCommentDto> reportPartyCommentDtos =
                    reportPartyCommentRepository.findByCommentPriKey(
                            userPriKey,
                            partyComment.get().getId()
                    );
            if (!reportPartyCommentDtos.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 신고한 댓글 입니다."
                );
            reportPartyCommentRepository.save(
                    ReportPartyComment.of(
                            null,
                            user,
                            partyComment.get()
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
