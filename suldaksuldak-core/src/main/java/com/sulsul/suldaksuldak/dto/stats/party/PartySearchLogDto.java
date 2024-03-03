package com.sulsul.suldaksuldak.dto.stats.party;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PartySearchLogDto {
    String id;
    Long userPriKey;
    String userNickname;
    Long partyPriKey;
    String partyName;
    LocalDateTime searchAt;
}
