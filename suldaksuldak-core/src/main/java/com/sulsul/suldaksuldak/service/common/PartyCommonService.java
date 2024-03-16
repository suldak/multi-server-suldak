package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyComplete;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.complete.PartyCompleteRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.service.level.LevelControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCommonService {
    private final CheckPriKeyService checkPriKeyService;
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;
    private final PartyCompleteRepository partyCompleteRepository;
    private final LevelControlService levelControlService;

    /**
     * 모임 시간, 상태에 따라서 모임의 상태와 인원에 대한 정보를 수정합니다.
     */
    public Boolean partyTotalHandler(
            Party party
    ) {
        try {
            Long partyPriKey = party.getId();
            PartyStateType partyStateType = party.getPartyStateType();
            // 모임 상태가 모집 중 일 때
            if (partyStateType.equals(PartyStateType.RECRUITING)) {
                // 승인된 인원이 3명 미만일 때
                if (getPartyConfirmCnt(partyPriKey) < 3) {
                    // 모든 모임 인원들의 상태를 취소로 설정
                    setAllPartyGuestType(
                            partyPriKey,
                            null,
                            GuestType.CANCEL
                    );
                    // 모임 상태를 모임 취소로 설정
                    modifiedPartyState(
                            party,
                            PartyStateType.MEETING_CANCEL
                    );
                    return false;
                } else {
                    // 모임 인원 중 대기 상태를 거절로 수정
                    setAllPartyGuestType(
                            partyPriKey,
                            GuestType.WAIT,
                            GuestType.REFUSE
                    );
                    // 모임 상태를 모집 중지로 수정
                    modifiedPartyState(
                            party,
                            PartyStateType.RECRUITMENT_END
                    );
                }
            }
            // 모임 상태가 모집 종료 일 때
            else if (partyStateType.equals(PartyStateType.RECRUITMENT_END)) {
                // 모임 인원 중 승인 상태를 모임 중으로 수정
                setAllPartyGuestType(
                        partyPriKey,
                        GuestType.CONFIRM,
                        GuestType.ON_GOING
                );
                // 모임 상태를 모임 중으로 수정
                modifiedPartyState(
                        party,
                        PartyStateType.ON_GOING
                );
            }
            // 모임 상태가 모임 중 일 때
            else if (partyStateType.equals(PartyStateType.ON_GOING)) {
                // 모임 인원 중 모임 중인 상태를 완료 대기로 수정
                setAllPartyGuestType(
                        partyPriKey,
                        GuestType.ON_GOING,
                        GuestType.COMPLETE_WAIT
                );
                // 모임 상태를 완료로 수정
                modifiedPartyState(
                        party,
                        PartyStateType.MEETING_COMPLETE
                );
            }
            // 모임 상태가 완료 일 때
            else if (partyStateType.equals(PartyStateType.MEETING_COMPLETE)) {
                // 모임 인원 중 완료 대기 상태를 완료로 수정
                setAllPartyGuestType(
                        partyPriKey,
                        GuestType.COMPLETE_WAIT,
                        GuestType.COMPLETE
                );
                // 모임이 최종 완료된 인원 정보 저장
                setPartyComplete(party);
                // 모임 참여 및 호스트 로 인한 레벨 증감 실행
                levelControlService.updateUserLevelFromComplete(
                        party.getId()
                );
            }
            // 모임 상태가 취소 및 삭제 일 때
            else {
                setAllPartyGuestType(
                        partyPriKey,
                        null,
                        GuestType.CANCEL
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

    /**
     * 모임의 상태를 수정합니다.
     */
    public Boolean modifiedPartyState(
            Party party,
            PartyStateType partyStateType
    ) {
        try {
            party.setPartyStateType(partyStateType);
            partyRepository.save(party);
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

    /**
     * 모임에 승인된 인원이 몇명인지 조회합니다.
     */
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

    /**'
     * 해당 모임의 인원들의 상태를 일괄 조회 및 수정
     */
    public Boolean setAllPartyGuestType(
            Long partyPriKey,
            GuestType findType,
            GuestType setType
    ) {
        try {
            List<PartyGuest> partyGuests =
                    partyGuestRepository.findByPartyPriKey(
                            partyPriKey,
                            findType
                    );
            for (PartyGuest partyGuest: partyGuests) {
                partyGuest.setConfirm(setType);
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

    public Boolean setPartyComplete(
            Party party
    ) throws GeneralException {
        try {
            List<PartyGuestDto> partyGuestDtos =
                    partyGuestRepository.findByOptions(
                            null,
                            null,
                            null,
                            null,
                            party.getId(),
                            null,
                            List.of(GuestType.COMPLETE)
                    );
            for (PartyGuestDto dto: partyGuestDtos) {
                try {
                    User user = checkPriKeyService.checkAndGetUser(dto.getGuestPriKey());
                    partyCompleteRepository.save(
                            PartyComplete.of(
                                    null,
                                    false,
                                    false,
                                    dto.getHostPriKey().equals(user.getId()),
                                    null,
                                    null,
                                    party,
                                    user
                            )
                    );
                } catch (Exception ignore) {}
            }
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
