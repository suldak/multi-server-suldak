package com.sulsul.suldaksuldak.repo.party.guest;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;

import java.util.List;
import java.util.Optional;

public interface PartyGuestRepositoryCustom {
    List<PartyGuestDto> findByOptions(
            Long partyPriKey,
            Long userPriKey,
            GuestType confirm
    );
}
