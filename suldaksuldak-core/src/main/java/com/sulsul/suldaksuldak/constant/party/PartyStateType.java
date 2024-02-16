package com.sulsul.suldaksuldak.constant.party;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyStateType {
    RECRUITING,
    RECRUITMENT_END,
    ON_GOING,
    MEETING_COMPLETE,
    MEETING_CANCEL,
    MEETING_DELETE
}
