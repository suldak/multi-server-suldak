package com.sulsul.suldaksuldak.constant.party;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyBatchType {
    SET_RECRUITMENT_END(1),
    SET_ON_GOING(2),
    SET_MEETING_COMPLETE(3),
    SET_GUEST_COMPLETE(4)
    ;
    private final Integer num;

    public static PartyBatchType findText(
            String text
    ) {
        if (text.equals(SET_RECRUITMENT_END.toString()))
            return SET_RECRUITMENT_END;
        if (text.equals(SET_ON_GOING.toString()))
            return SET_ON_GOING;
        if (text.equals(SET_MEETING_COMPLETE.toString()))
            return SET_MEETING_COMPLETE;
        if (text.equals(SET_GUEST_COMPLETE.toString()))
            return SET_GUEST_COMPLETE;
        return null;
    }
}
