package com.sulsul.suldaksuldak.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobIdentity {
    // 모집 종료 스케줄 TRIGGER
    // 모집 종료 스케줄 JOB 이름
    PARTY_BATCH_TRIGGER_RECRUITMENT_END("PARTY_BATCH_TRIGGER_RECRUITMENT_END"),
    PARTY_BATCH_JOB_RECRUITMENT_END("PARTY_BATCH_JOB_RECRUITMENT_END"),

    // 모임 중 스케줄 TRIGGER
    // 모임 중 스케줄 JOB 이름
    PARTY_BATCH_TRIGGER_ON_GOING("PARTY_BATCH_TRIGGER_ON_GOING"),
    PARTY_BATCH_JOB_ON_GOING("PARTY_BATCH_JOB_ON_GOING"),

    // 모임 완료 스케줄 TRIGGER
    // 모임 완료 스케줄 JOB 이름
    PARTY_BATCH_TRIGGER_MEETING_COMPLETE("PARTY_BATCH_TRIGGER_MEETING_COMPLETE"),
    PARTY_BATCH_JOB_MEETING_COMPLETE("PARTY_BATCH_JOB_MEETING_COMPLETE"),

    // 모임 인원 완료 스케줄 TRIGGER
    // 모임 인원 완료 JOB 이름
    PARTY_BATCH_TRIGGER_GUEST_COMPLETE("PARTY_BATCH_TRIGGER_GUEST_COMPLETE"),
    PARTY_BATCH_JOB_GUEST_COMPLETE("PARTY_BATCH_JOB_GUEST_COMPLETE"),
    ;

    private final String name;
}
