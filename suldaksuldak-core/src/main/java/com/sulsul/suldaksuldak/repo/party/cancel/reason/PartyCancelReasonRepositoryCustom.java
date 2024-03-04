package com.sulsul.suldaksuldak.repo.party.cancel.reason;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelReasonDto;

import java.util.List;

public interface PartyCancelReasonRepositoryCustom {
    List<PartyCancelReasonDto> findByPartyRoleType(
            PartyRoleType partyRoleType
    );
}
