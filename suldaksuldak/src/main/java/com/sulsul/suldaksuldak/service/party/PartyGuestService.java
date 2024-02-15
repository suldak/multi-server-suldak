package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyGuestService {
    private final PartyService partyService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;

    /**
     * 모임 참가 신청
     */
    public Boolean participationParty(
            Long partyPriKey,
            Long userPriKey
    ) {
        try {
            if (partyPriKey == null || userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 유저를 찾을 수 없습니다."
                );
            Optional<Party> partyOptional = partyRepository.findById(partyPriKey);
            if (partyOptional.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임을 찾을 수 없습니다."
                );
            Party party = partyService.checkParty(partyOptional.get());
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 모집이 완료된 모임입니다."
                );
            if (partyOptional.get().getUser().getId().equals(user.get().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );
            if (!checkPartyPersonnel(party))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "인원이 모두 모집되었습니다."
                );
            List<PartyGuestDto> partyGuestDtos =
                    partyGuestRepository.findByOptions(
                            null,
                            null,
                            null,
                            null,
                            partyOptional.get().getId(),
                            user.get().getId(),
                            null
                    );
            if (!partyGuestDtos.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 신청한 모임입니다."
                );

            String dateStr = UtilTool.getLocalDateTimeString();

            partyGuestRepository.save(
                    PartyGuest.of(
                            dateStr + "_" + partyPriKey + "_" + userPriKey,
                            partyOptional.get(),
                            user.get(),
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
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾지 못했습니다."
                );
            if (guestPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "유저 기본키를 찾지 못했습니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findById(priKey);
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임을 찾을 수 없습니다."
                );
            Party party = partyService.checkParty(partyGuest.get().getParty());
            if (party.getUser().getId().equals(guestPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 모집이 종료된 모임입니다."
                );
            if (!partyGuest.get().getUser().getId().equals(guestPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "본인만 모임을 취소할 수 있습니다."
                );
            if (
                    partyGuest.get().getConfirm().equals(GuestType.COMPLETE) ||
                            partyGuest.get().getConfirm().equals(GuestType.COMPLETE_WAIT)
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 종료된 모임 입니다."
                );
            partyGuest.get().setConfirm(GuestType.CANCEL);
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

    public Integer getPartyConfirmCnt(
            Long partyPriKey
    ) {
        try {
            return partyGuestRepository.findByPartyPriKey(
                    partyPriKey,
                    GuestType.CONFIRM
            ).size();
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
     * 모임 신청 상태가 대기인 사람들을 거절로 수정
     */
    public Boolean setPartyGuestTypeRefuse(
            Long partyPriKey
    ) {
        try {
            List<PartyGuest> partyGuests =
                    partyGuestRepository.findByPartyPriKey(
                            partyPriKey,
                            GuestType.WAIT
                    );
            for (PartyGuest partyGuest: partyGuests) {
                partyGuest.setConfirm(GuestType.REFUSE);
                partyGuestRepository.save(partyGuest);
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
     * 모임 신청 인원들의 상태를 일괄 변환
     */
    public Boolean setPartyGuestTypeAll(
            Long partyPriKey,
            GuestType guestType
    ) {
        try {
            List<PartyGuest> partyGuests =
                    partyGuestRepository.findByPartyPriKey(
                            partyPriKey,
                            null
                    );
            for (PartyGuest partyGuest: partyGuests) {
                partyGuest.setConfirm(guestType);
                partyGuestRepository.save(partyGuest);
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
     * 모임 신청 상태가 승인인 인원들을 완료 대기로 수정
     */
    public Boolean setPartyGuestTypeComplete(
            Long partyPriKey
    ) {
        try {
            List<PartyGuest> partyGuests =
                    partyGuestRepository.findByPartyPriKey(
                            partyPriKey,
                            GuestType.CONFIRM
                    );
            for (PartyGuest partyGuest: partyGuests) {
                partyGuest.setConfirm(GuestType.COMPLETE_WAIT);
                partyGuestRepository.save(partyGuest);
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
     * 모임 인원이 모두 모집되었는지 확인합니다.
     */
    public Boolean checkPartyPersonnel(
            Party party
    ) {
        return getPartyConfirmCnt(party.getId())
                < party.getPersonnel();
    }
}
