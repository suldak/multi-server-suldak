package com.sulsul.suldaksuldak.repo.party.complete;

import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteDto;
import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteGroupDto;

import java.util.List;
import java.util.Optional;

public interface PartyCompleteRepositoryCustom {
    List<PartyCompleteDto> findByPartyPriKey(Long partyPriKey);

    Optional<PartyCompleteGroupDto> findUserGroupByUserPriKey(
            Long userPriKey,
            Boolean isCheckHost
    );
}
