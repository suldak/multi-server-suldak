package com.sulsul.suldaksuldak.constant.party;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GuestType {
    WAIT,
    CONFIRM,
    REFUSE,
    ON_GOING,
    COMPLETE,
    COMPLETE_WAIT,
    CANCEL
}
