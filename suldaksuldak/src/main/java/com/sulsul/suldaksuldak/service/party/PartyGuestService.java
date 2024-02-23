package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyGuestService {
    private final CheckPriKeyService checkPriKeyService;
    private final PartyService partyService;
    private final PartyGuestRepository partyGuestRepository;
    private final PartyCommonService partyCommonService;

    /**
     * 모임 참가 신청
     */
    public Boolean participationParty(
            Long partyPriKey,
            Long userPriKey
    ) {
        try {
            Party party = partyService.checkParty(partyPriKey, true);
            User user = checkPriKeyService.checkAndGetUser(userPriKey);
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 모집이 완료된 모임입니다."
                );
            if (party.getUser().getId().equals(user.getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );
            if (!partyCommonService.checkPartyPersonnel(party))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "인원이 모두 모집되었습니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findByUserPriKeyAndPartyPriKey(
                            user.getId(),
                            party.getId()
                    );
            if (partyGuest.isPresent())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 신청한 모임입니다."
                );
            String dateStr = UtilTool.getLocalDateTimeString();
            partyGuestRepository.save(
                    PartyGuest.of(
                            dateStr + "_" + partyPriKey + "_" + userPriKey,
                            party,
                            user,
                            GuestType.WAIT
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

    /**
     * 모임 신청을 취소합니다.
     */
    public Boolean partyCancel(
            Long guestPriKey,
            String priKey
    ) {
        try {
            PartyGuest partyGuest = checkPriKeyService.checkAndGetPartyGuest(priKey);
            Party party = partyService.checkParty(partyGuest.getParty());
            if (party.getUser().getId().equals(guestPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );
            if (!partyGuest.getUser().getId().equals(guestPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "본인만 모임을 취소할 수 있습니다."
                );
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 모집이 종료된 모임입니다."
                );
            if (
                    partyGuest.getConfirm().equals(GuestType.COMPLETE) ||
                            partyGuest.getConfirm().equals(GuestType.COMPLETE_WAIT)
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 종료된 모임 입니다."
                );
            partyGuest.setConfirm(GuestType.CANCEL);
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
}
