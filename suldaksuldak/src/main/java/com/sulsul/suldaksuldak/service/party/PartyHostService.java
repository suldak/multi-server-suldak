package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyHostService {
    private final PartyService partyService;
    private final PartyGuestService partyGuestService;
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;
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
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾지 못했습니다."
                );
            if (confirm == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "confirm 값이 NULL 입니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findById(priKey);
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임을 찾을 수 없습니다."
                );
            Party party = partyService.checkParty(partyGuest.get().getParty());
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            if (!hostPriKey.equals(partyGuest.get().getParty().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임의 호스트가 아닙니다."
                );
            if (
                    confirm.equals(GuestType.CONFIRM) &&
                    !partyGuestService.checkPartyPersonnel(partyGuest.get().getParty())
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "인원이 모두 모집되었습니다."
                );
            partyGuest.get().setConfirm(confirm);
            partyGuestRepository.save(partyGuest.get());
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
     * 모임의 상태를 수정합니다.
     */
    public Boolean modifiedPartyState(
            Long partyPriKey,
            Long hostPriKey,
            PartyStateType partyStateType
    ) {
        try {
            Party party = partyService.checkParty(partyPriKey, false);
            if (partyGuestService.getPartyConfirmCnt(party.getId()) < 3)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "승인된 인원이 최소 3명 부터 모집을 종료할 수 있습니다."
                );
            if (!party.getUser().getId().equals(hostPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "호스트만 모임의 상태를 수정할 수 있습니다."
                );
            party.setPartyStateType(partyStateType);
            partyRepository.save(party);
            // 모임 모집 완료 처리
            if (partyStateType.equals(PartyStateType.RECRUITMENT_END))
                partyGuestService.setPartyGuestTypeRefuse(partyPriKey);
            // 모임 완료 처리
            else if (partyStateType.equals(PartyStateType.MEETING_COMPLETE))
                partyGuestService.setPartyGuestTypeComplete(partyPriKey);
            // 모임 취소 처리
            // 모임 삭제 처리
            else if (
                    partyStateType.equals(PartyStateType.MEETING_CANCEL) ||
                            partyStateType.equals(PartyStateType.MEETING_DELETE)
            )
                partyGuestService.setPartyGuestTypeAll(
                        partyPriKey,
                        GuestType.CANCEL
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
