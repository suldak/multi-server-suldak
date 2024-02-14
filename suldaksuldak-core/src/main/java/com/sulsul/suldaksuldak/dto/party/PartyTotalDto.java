package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PartyTotalDto {
    Long id;
    String name;
    LocalDateTime meetingDay;
    Integer personnel;
    String introStr;
    PartyType partyType;
    String partyPlace;
    String contactType;
    String useProgram;
    String onlineUrl;
    Long hostUserPriKey;
    String hostUserName;
    String hostFileName;
    String fileBaseNm;
    Long tagPriKey;
    String tagName;
    Long warningCnt;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    Long confirmCnt;
    GuestType guestType;
}
