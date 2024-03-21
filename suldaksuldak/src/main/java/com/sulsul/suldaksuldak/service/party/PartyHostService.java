package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancelReason;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.cancel.PartyCancelRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import com.sulsul.suldaksuldak.service.level.LevelControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyHostService {
    private final CheckPriKeyService checkPriKeyService;
    private final PartyService partyService;
    private final PartyGuestRepository partyGuestRepository;
    private final PartyCommonService partyCommonService;
    private final PartyCancelRepository partyCancelRepository;
    private final LevelControlService levelControlService;

    /**
     * 관계 기본기
     * 모임 참가 인원의 상태를 수정합니다.
     */
    public Boolean modifiedPartyGuest (
            Long hostPriKey,
            String priKey,
            GuestType confirm
    ) {
        try {
            if (confirm == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "confirm 값이 NULL 입니다."
                );
            PartyGuest partyGuest = checkPriKeyService.checkAndGetPartyGuest(priKey);
            Party party = partyService.checkParty(partyGuest.getParty());
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            if (!hostPriKey.equals(partyGuest.getParty().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임의 호스트가 아닙니다."
                );
            if (hostPriKey.equals(partyGuest.getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );
            if (!partyGuest.getConfirm().equals(GuestType.WAIT))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 유저는 대기 상태가 아닙니다."
                );
            if (
                    confirm.equals(GuestType.CONFIRM) &&
                    !partyCommonService.checkPartyPersonnel(partyGuest.getParty())
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "인원이 모두 모집되었습니다."
                );
            partyGuest.setConfirm(confirm);
            partyGuestRepository.save(partyGuest);
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
     * 모임의 상태를 모집 졸료 상태로 수정합니다.
     */
    public Boolean modifiedRecruitmentEndParty(
            Long partyPriKey,
            Long hostPriKey
    ) {
        try {
            Party party = partyService.checkParty(partyPriKey, false);
            if (!party.getUser().getId().equals(hostPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "호스트만 모임의 상태를 수정할 수 있습니다."
                );
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            if (partyCommonService.getPartyConfirmCnt(party.getId()) < 3)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "승인된 인원이 최소 3명 부터 모집을 종료할 수 있습니다."
                );
            return partyCommonService.partyTotalHandler(
                    party
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

    /**
     * 모임의 상태를 모임 취소로 수정합니다.
     */
    @Transactional
    public Boolean modifiedCancelParty(
            Long partyPriKey,
            Long hostPriKey,
            PartyStateType partyStateType,
            PartyCancelDto partyCancelDto
    ) {
        try {
            Party party = partyService.checkParty(partyPriKey, true);
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            if (!party.getUser().getId().equals(hostPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "호스트만 모임의 상태를 수정할 수 있습니다."
                );

            PartyCancelReason partyCancelReason =
                    checkPriKeyService.checkAndGetPartyCancelReason(
                            partyCancelDto.getPartyCancelReasonPriKey()
                    );
            if (!partyCancelReason.getPartyRoleType().equals(PartyRoleType.HOST))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "호스트 모임 취소 이유로 시도해 주세요."
                );
//            party.setPartyStateType(partyStateType);
            partyCommonService.modifiedPartyState(party, partyStateType);
            partyCancelRepository.save(
                    partyCancelDto.toEntity(
                            partyCancelReason,
                            party,
                            party.getUser()
                    )
            );
            try {
                if (LocalDateTime.now().isAfter(party.getMeetingDay().minusDays(1))) {
                    levelControlService.updateUserWarningCnt(
                            party.getUser(),
                            1.0
                    );
                }
            } catch (Exception ignore) {}
            return partyCommonService.partyTotalHandler(
                    party
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
