package com.sulsul.suldaksuldak.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobKey {
    PARTY_PRI_KEY("id"),
    PARTY_BATCH_TYPE("partyBatchType")
    ;
    private final String keyStr;
}
