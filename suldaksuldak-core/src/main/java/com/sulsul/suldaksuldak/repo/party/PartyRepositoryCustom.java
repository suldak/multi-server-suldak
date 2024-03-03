package com.sulsul.suldaksuldak.repo.party;

import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartyRepositoryCustom {
    Page<PartyDto> findByOptional(
            List<Long> notShowParty,
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            List<Long> partyTagPriList,
            List<PartyStateType> partyStateTypes,
            Boolean sortBool,
            Pageable pageable
    );

    List<PartyDto> findByPriKeyList(
            List<Long> priKeyList
    );

    List<PartyDto> findByPriKeyAndGuestPriKey(
            List<Long> priKeyList,
            Long guestPriKey,
            Boolean sortBool
    );

    List<PartyDto> findByHostPriKey(
            Long hostPriKey,
            Boolean sortBool
    );

    Optional<PartyDto> findByPriKey(
            Long priKey
    );

    List<PartyDto> findByHostLevel(
            Integer limitNum
    );

    List<PartyDto> findByTagPriKeyList(
            List<Long> tagPriKeyList,
            Integer limitNum
    );
}
