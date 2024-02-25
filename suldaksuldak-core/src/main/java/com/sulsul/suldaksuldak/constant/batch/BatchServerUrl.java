package com.sulsul.suldaksuldak.constant.batch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BatchServerUrl {
    PARTY_SCHEDULE_URL("/api/batch/party/"),
    ;

    private final String url;
}
