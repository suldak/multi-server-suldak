package com.sulsul.suldaksuldak.constant.party;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyBatchType {
    SET_RECRUITMENT_END,
    SET_ON_GOING,
    SET_MEETING_COMPLETE,
    SET_GUEST_COMPLETE
    ;
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
