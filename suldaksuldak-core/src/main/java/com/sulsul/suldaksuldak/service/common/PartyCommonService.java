package com.sulsul.suldaksuldak.service.common;

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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCommonService {
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;

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

//    /**
//     * 모임 신청 인원들의 상태를 일괄 변환
//     */
//    public Boolean setPartyGuestTypeAll(
//            Long partyPriKey,
//            GuestType guestType
//    ) {
//        try {
//            List<PartyGuest> partyGuests =
//                    partyGuestRepository.findByPartyPriKey(
//                            partyPriKey,
//                            null
//                    );
//            for (PartyGuest partyGuest: partyGuests) {
//                partyGuest.setConfirm(guestType);
//                partyGuestRepository.save(partyGuest);
//            }
//            return true;
//        } catch (GeneralException e) {
//            throw new GeneralException(
//                    e.getErrorCode(),
//                    e.getMessage()
//            );
//        } catch (Exception e) {
//            throw new GeneralException(
//                    ErrorCode.DATA_ACCESS_ERROR,
//                    e.getMessage()
//            );
//        }
//    }

//    /**
//     * 모임 인원들을 대기 > 거절
//     */
//    public Boolean setPartyGuestTypeRefuse(
//            Long partyPriKey
//    ) {
//        try {
//            List<PartyGuest> partyGuests =
//                    partyGuestRepository.findByPartyPriKey(
//                            partyPriKey,
//                            GuestType.WAIT
//                    );
//            for (PartyGuest partyGuest: partyGuests) {
//                partyGuest.setConfirm(GuestType.REFUSE);
//                partyGuestRepository.save(partyGuest);
//            }
//            return true;
//        } catch (GeneralException e) {
//            throw new GeneralException(
//                    e.getErrorCode(),
//                    e.getMessage()
//            );
//        } catch (Exception e) {
//            throw new GeneralException(
//                    ErrorCode.DATA_ACCESS_ERROR,
//                    e.getMessage()
//            );
//        }
//    }
//
//    /**
//     * 모임 인원들을 승인 > 모임 중
//     */
//    public Boolean setPartyGuestTypeOnGoing(
//            Long partyPriKey
//    ) {
//        try {
//            List<PartyGuest> partyGuests =
//                    partyGuestRepository.findByPartyPriKey(
//                            partyPriKey,
//                            GuestType.CONFIRM
//                    );
//            for (PartyGuest partyGuest: partyGuests) {
//                partyGuest.setConfirm(GuestType.ON_GOING);
//                partyGuestRepository.save(partyGuest);
//            }
//            return true;
//        } catch (GeneralException e) {
//            throw new GeneralException(
//                    e.getErrorCode(),
//                    e.getMessage()
//            );
//        } catch (Exception e) {
//            throw new GeneralException(
//                    ErrorCode.DATA_ACCESS_ERROR,
//                    e.getMessage()
//            );
//        }
//    }
//
//    /**
//     * 모임 인원들을 모임 중 > 완료 대기
//     */
//    public Boolean setPartyGuestTypeCompleteWait(
//            Long partyPriKey
//    ) {
//        try {
//            List<PartyGuest> partyGuests =
//                    partyGuestRepository.findByPartyPriKey(
//                            partyPriKey,
//                            GuestType.ON_GOING
//                    );
//            for (PartyGuest partyGuest: partyGuests) {
//                partyGuest.setConfirm(GuestType.COMPLETE_WAIT);
//                partyGuestRepository.save(partyGuest);
//            }
//            return true;
//        } catch (GeneralException e) {
//            throw new GeneralException(
//                    e.getErrorCode(),
//                    e.getMessage()
//            );
//        } catch (Exception e) {
//            throw new GeneralException(
//                    ErrorCode.DATA_ACCESS_ERROR,
//                    e.getMessage()
//            );
//        }
//    }
//
//    /**
//     * 모임 인원들을 완료 대기 > 완료
//     */
//    public Boolean setPartyGuestTypeComplete(
//            Long partyPriKey
//    ) {
//        try {
//            List<PartyGuest> partyGuests =
//                    partyGuestRepository.findByPartyPriKey(
//                            partyPriKey,
//                            GuestType.COMPLETE_WAIT
//                    );
//            for (PartyGuest partyGuest: partyGuests) {
//                partyGuest.setConfirm(GuestType.COMPLETE);
//                partyGuestRepository.save(partyGuest);
//            }
//            return true;
//        } catch (GeneralException e) {
//            throw new GeneralException(
//                    e.getErrorCode(),
//                    e.getMessage()
//            );
//        } catch (Exception e) {
//            throw new GeneralException(
//                    ErrorCode.DATA_ACCESS_ERROR,
//                    e.getMessage()
//            );
//        }
//    }
}
