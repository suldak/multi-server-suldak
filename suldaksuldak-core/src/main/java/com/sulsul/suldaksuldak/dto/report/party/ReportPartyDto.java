package com.sulsul.suldaksuldak.dto.report.party;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReportPartyDto {
    Long id;
    Long userPriKey;
    String userNickname;
    String userFileNm;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    Long reasonPriKey;
    String reason;
    Boolean complete;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static ReportPartyDto of (
            Long reasonPriKey
    ) {
        return new ReportPartyDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                reasonPriKey,
                null,
                null,
                null,
                null
        );
    }
}
