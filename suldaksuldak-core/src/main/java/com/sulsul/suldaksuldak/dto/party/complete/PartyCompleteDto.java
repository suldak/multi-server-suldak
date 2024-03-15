package com.sulsul.suldaksuldak.dto.party.complete;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PartyCompleteDto {
    Long id;
    Boolean isCompleteProcessed;
    Boolean isHostProcessed;
    Boolean isHost;
    LocalDateTime processedAt;
    Long partyPriKey;
    Long userPriKey;
}
