package com.sulsul.suldaksuldak.constant.batch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BatchServerUrl {
    PARTY_SCHEDULE_URL("/api/batch/party/"),
    PARTY_SCHEDULE_RECRUITMENT_END_URL("/api/batch/party/recruitment-end/"),
    ;

    private final String url;
}
