package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.comment.PartyCommentRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyAdminService {
    private final CheckPriKeyService checkPriKeyService;
    private final PartyCommonService partyCommonService;
    private final PartyCommentRepository partyCommentRepository;

    public Boolean deleteParty(
            Long partyPriKey
    ) {
        try {
            if (partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            party.setPartyStateType(PartyStateType.MEETING_DELETE);
            partyCommonService.partyTotalHandler(party);
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    public Boolean deletePartyComment(
            String commentPriKey
    ) {
        try {
            if (commentPriKey == null || commentPriKey.isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            partyCommentRepository.findById(commentPriKey)
                    .ifPresentOrElse(
                            findComment -> {
                                findComment.setIsDelete(true);
                                partyCommentRepository.save(findComment);
                            },
                            () -> {
                                throw new GeneralException(
                                        ErrorCode.NOT_FOUND,
                                        "해당 댓글을 찾지 못했습니다."
                                );
                            }
                    );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}
