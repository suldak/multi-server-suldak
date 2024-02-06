package com.sulsul.suldaksuldak.dto.party.guest;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

@Value
public class PartyGuestDto {
    String id;
    Long partyPriKey;
    String partyName;
    Long hostPriKey;
    String hostNickname;
    String hostFileNm;
    Long guestPriKey;
    String guestNickname;
    String guestFileNm;
    GuestType confirmState;

    public PartyGuestDto(
            String id,
            Long partyPriKey,
            String partyName,
            User user,
            Long guestPriKey,
            String guestNickname,
            String guestFileNm,
            GuestType confirmState
    ) {
        this.id = id;
        this.partyPriKey = partyPriKey;
        this.partyName = partyName;
        this.hostPriKey = user.getId();
        this.hostNickname = user.getNickname();
        this.hostFileNm = user.getFileBase() == null ? null : user.getFileBase().getFileNm();
        this.guestPriKey = guestPriKey;
        this.guestNickname = guestNickname;
        this.guestFileNm = guestFileNm;
        this.confirmState = confirmState;
    }
}
