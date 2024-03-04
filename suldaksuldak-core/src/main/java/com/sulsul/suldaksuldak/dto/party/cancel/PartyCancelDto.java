package com.sulsul.suldaksuldak.dto.party.cancel;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancel;
import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancelReason;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

@Value
public class PartyCancelDto {
    Long id;
    Long partyCancelReasonPriKey;
    PartyRoleType partyRoleType;
    String partyCancelReason;
    String detailReason;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    Long userPriKey;
    String userNickname;
    String userFileNm;

    public static PartyCancelDto of (
            Long partyCancelReasonPriKey,
            String detailReason,
            Long partyPriKey,
            Long userPriKey
    ) {
        return new PartyCancelDto(
                null,
                partyCancelReasonPriKey,
                null,
                null,
                detailReason,
                partyPriKey,
                null,
                null,
                userPriKey,
                null,
                null
        );
    }

    public PartyCancel toEntity(
            PartyCancelReason partyCancelReason,
            Party party,
            User user
    ) {
        return PartyCancel.of(
                id,
                partyCancelReason,
                detailReason,
                party,
                user
        );
    }
}
