package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancelReason;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.cancel.reason.PartyCancelReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCancelAdminService {
    private final PartyCancelReasonRepository partyCancelReasonRepository;

    /**
     * 모임 취소 이유 생성 및 수정
     */
    public Boolean createPartyCancelReason(
            Long id,
            String reason,
            PartyRoleType partyRoleType
    ) {
        try {
            if (id == null) {
                partyCancelReasonRepository.save(
                        PartyCancelReason.of(
                                null,
                                reason,
                                partyRoleType
                        )
                );
            } else {
                partyCancelReasonRepository.findById(id)
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (reason != null) findEntity.setReason(reason);
                                    if (partyRoleType != null) findEntity.setPartyRoleType(partyRoleType);

                                    partyCancelReasonRepository.save(findEntity);
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "해당 데이터를 찾지 못했습니다."
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

    public Boolean deletePartyCancelReason(
            Long priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            partyCancelReasonRepository.deleteById(priKey);
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
