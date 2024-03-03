package com.sulsul.suldaksuldak.repo.stats.party;

import com.sulsul.suldaksuldak.dto.stats.party.PartySearchLogDto;

import java.util.List;
import java.util.Optional;

public interface PartySearchLogRepositoryCustom {
    Optional<PartySearchLogDto> findLastByUserPriKeyAndPartyPriKey(
            Long userPriKey,
            Long partyPriKey
    );

    List<Long> findPartyPriKeyByTopSearch(
            Integer limitNum
    );
}
