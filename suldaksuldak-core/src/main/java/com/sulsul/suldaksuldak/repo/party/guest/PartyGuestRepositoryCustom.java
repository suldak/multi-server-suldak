package com.sulsul.suldaksuldak.repo.party.guest;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartyGuestRepositoryCustom {
    List<PartyGuestDto> findByOptions(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            PartyType partyType,
            List<Long> partyTagPriList,
            Long partyPriKey,
            Long userPriKey,
            List<GuestType> confirmList
    );

    List<PartyGuest> findByPartyPriKey(
            Long partyPriKey,
            GuestType confirm
    );

    Optional<PartyGuest> findByUserPriKeyAndPartyPriKey(
            Long userPriKey,
            Long partyPriKey
    );

    List<Long> findPartyPriKeyByTopGuestCount(
            Integer limitNum
    );

    List<Long> findTagPriKeyByUserRecommend(
            Long userPriKey,
            Integer limitNum
    );
}
