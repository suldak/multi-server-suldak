package com.sulsul.suldaksuldak.repo.party.cancel;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelDto;

import java.util.List;

public interface PartyCancelRepositoryCustom {
    List<PartyCancelDto> findByOptions(
            PartyRoleType partyRoleType,
            String detailReason,
            Long partyPriKey,
            Long userPriKeys,
            Long partyCancelReasonPriKey
    );
}
