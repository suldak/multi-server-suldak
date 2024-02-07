package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyViewService {
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;

    /**
     * 모임 목록 조회
     */
    public Page<PartyRes> getPartyPageList(
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            List<Long> partyTagPriList,
            Pageable pageable
    ) {
        try {
            Page<PartyDto> partyDtos = partyRepository.findByOptional(
                    name,
                    searchStartTime,
                    searchEndTime,
                    personnel,
                    partyType,
                    hostUserPriKey,
                    partyTagPriList,
                    pageable
            );

            return new PageImpl<>(
                    partyDtos.getContent().stream().map(
                            PartyRes::from
                    ).toList(),
                    partyDtos.getPageable(),
                    partyDtos.getTotalElements()
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
     * 파티 참가원 목록 조회
     */
    public List<PartyGuestDto> getPartyGuestList(
            Long partyPriKey,
            Long userPriKey,
            GuestType confirm
    ) {
        try {
            return partyGuestRepository.findByOptions(
                    partyPriKey,
                    userPriKey,
                    confirm
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
     * 유저가 참가하는 모임 목록 조회
     */
    public List<PartyDto> getPartyByUser(
            Long userPriKey,
            GuestType confirm
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            List<Long> partyPriKeyList =
                    partyGuestRepository.findByOptions(
                            null,
                            userPriKey,
                            confirm
                    ).stream().map(PartyGuestDto::getPartyPriKey).toList();
            return partyRepository.findByPriKeyList(partyPriKeyList);
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
     * 유저가 Host인 모임 목록
     */
    public List<PartyDto> getPartyByHostPriKey(
            Long userPriKey
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            return partyRepository.findByHostPriKey(userPriKey);
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
