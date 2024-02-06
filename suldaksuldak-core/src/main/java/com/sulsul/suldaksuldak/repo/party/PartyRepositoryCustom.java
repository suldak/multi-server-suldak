package com.sulsul.suldaksuldak.repo.party;

import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PartyRepositoryCustom {
    Page<PartyDto> findByOptional(
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            Pageable pageable
    );

    List<PartyDto> findByPriKeyList(
            List<Long> priKeyList
    );

    List<PartyDto> findByHostPriKey(
            Long hostPriKey
    );
}
