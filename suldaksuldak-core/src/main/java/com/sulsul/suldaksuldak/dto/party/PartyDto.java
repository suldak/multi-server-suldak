package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PartyDto {
    Long id;
    String name;
    LocalDateTime meetingDay;
    Integer personnel;
    String introStr;
    PartyType partyType;
    String partyPlace;
    String contactType;
    String useProgram;
    String onlineUrl;
    Long hostUserPriKey;
    String hostUserName;
    String fileBaseNm;
    Long tagPriKey;
    String tagName;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static PartyDto of (
            Long id,
            String name,
            LocalDateTime meetingDay,
            Integer personnel,
            String introStr,
            PartyType partyType,
            String partyPlace,
            String contactType,
            String useProgram,
            String onlineUrl,
            Long hostUserPriKey,
            Long tagPriKey
    ) {
        return new PartyDto(
                id,
                name,
                meetingDay,
                personnel,
                introStr,
                partyType,
                partyPlace,
                contactType,
                useProgram,
                onlineUrl,
                hostUserPriKey,
                null,
                null,
                tagPriKey,
                null,
                null,
                null
        );
    }

    public Party toEntity(
            User user,
            FileBase fileBase,
            PartyTag partyTag
    ) {
        return Party.of(
                id,
                name,
                meetingDay,
                personnel,
                introStr,
                partyType,
                partyPlace,
                contactType,
                useProgram,
                onlineUrl,
                user,
                fileBase,
                partyTag
        );
    }

    public Party updateEntity(
            Party party
    ) {
        if (name != null) party.setName(name);
        if (meetingDay != null) party.setMeetingDay(meetingDay);
        if (personnel != null) party.setPersonnel(personnel);
        if (introStr != null) party.setIntroStr(introStr);
        if (partyPlace != null) party.setPartyPlace(partyPlace);
        if (contactType != null) party.setContactType(contactType);
        if (useProgram != null) party.setUseProgram(useProgram);
        if (onlineUrl != null) party.setOnlineUrl(onlineUrl);
        return party;
    }

    public Party updatePartyPicture(
            Party party,
            FileBase fileBase
    ) {
        party.setFileBase(fileBase);
        return party;
    }
}
