package com.sulsul.suldaksuldak.repo.search.tag;

import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;

import java.util.List;

public interface PartyGuestRepositoryCustom {
    List<PartyGuestDto> findByOptions(
            Long partyPriKey,
            Long userPriKey,
            Boolean confirm
    );
}
