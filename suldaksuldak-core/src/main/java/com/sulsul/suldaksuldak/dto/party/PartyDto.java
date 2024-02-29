package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
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
    // OFF_LINE
    String partyPlace;
    String contactType;
    // ON_LINE
    String useProgram;
    // ON_LINE
    String onlineUrl;
    PartyStateType partyStateType;
    Long hostUserPriKey;
    String hostUserName;
    String hostFileName;
    String fileBaseNm;
    Long tagPriKey;
    String tagName;
    Long warningCnt;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    Long confirmCnt;
    GuestType guestType;

    public PartyDto(
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
            PartyStateType partyStateType,
            Long hostUserPriKey,
            String hostUserName,
            String hostFileName,
            String fileBaseNm,
            Long tagPriKey,
            String tagName,
            Long warningCnt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long confirmCnt,
            GuestType guestType
    ) {
        this.id = id;
        this.name = name;
        this.meetingDay = meetingDay;
        this.personnel = personnel;
        this.introStr = introStr;
        this.partyType = partyType;
        this.partyPlace = partyPlace;
        this.contactType = contactType;
        this.useProgram = useProgram;
        this.onlineUrl = onlineUrl;
        this.partyStateType = partyStateType;
        this.hostUserPriKey = hostUserPriKey;
        this.hostUserName = hostUserName;
        this.hostFileName = hostFileName;
        this.fileBaseNm = fileBaseNm;
        this.tagPriKey = tagPriKey;
        this.tagName = tagName;
        this.warningCnt = warningCnt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.confirmCnt = confirmCnt;
        this.guestType = guestType;
    }

    public PartyDto(
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
            PartyStateType partyStateType,
            Long hostUserPriKey,
            String hostUserName,
            String hostFileName,
            String fileBaseNm,
            Long tagPriKey,
            String tagName,
            Long warningCnt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long confirmCnt
    ) {
        this.id = id;
        this.name = name;
        this.meetingDay = meetingDay;
        this.personnel = personnel;
        this.introStr = introStr;
        this.partyType = partyType;
        this.partyPlace = partyPlace;
        this.contactType = contactType;
        this.useProgram = useProgram;
        this.onlineUrl = onlineUrl;
        this.partyStateType = partyStateType;
        this.hostUserPriKey = hostUserPriKey;
        this.hostUserName = hostUserName;
        this.hostFileName = hostFileName;
        this.fileBaseNm = fileBaseNm;
        this.tagPriKey = tagPriKey;
        this.tagName = tagName;
        this.warningCnt = warningCnt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.confirmCnt = confirmCnt;
        this.guestType = null;
    }

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
            PartyStateType partyStateType,
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
                partyStateType,
                hostUserPriKey,
                null,
                null,
                null,
                tagPriKey,
                null,
                null,
                null,
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
                partyStateType,
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
