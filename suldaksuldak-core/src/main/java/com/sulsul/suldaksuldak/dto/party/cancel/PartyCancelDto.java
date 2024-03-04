package com.sulsul.suldaksuldak.dto.party.cancel;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import lombok.Value;

@Value
public class PartyCancelDto {
    Long id;
    Long partyCancelReasonPriKey;
    PartyRoleType partyRoleType;
    String partyCancelReason;
    String detailReason;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    Long userPriKey;
    String userNickname;
    String userFileNm;
}
