package com.sulsul.suldaksuldak.dto.party.guest;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import lombok.Value;

@Value
public class PartyGuestDto {
    String id;
    Long partyPriKey;
    String partyName;
    Long userPriKey;
    String userNickname;
    String fileNm;
    GuestType confirmState;
}
