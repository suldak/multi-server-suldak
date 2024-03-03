package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "모임 Response")
public class PartyRes {
    @ApiModelProperty(value = "모임 기본키")
    Long id;
    @ApiModelProperty(value = "모임 이름")
    String name;
    @ApiModelProperty(value = "모임 일사")
    LocalDateTime meetingDay;
    @ApiModelProperty(value = "모임 인원")
    Integer personnel;
    @ApiModelProperty(value = "모임 소개글")
    String introStr;
    @ApiModelProperty(value = "모임 타입")
    PartyType partyType;
    @ApiModelProperty(value = "모임 장소 (오프라인)")
    String partyPlace;
    @ApiModelProperty(value = "연락수단")
    String contactType;
    @ApiModelProperty(value = "사용 프로그램 (온라인)")
    String useProgram;
    @ApiModelProperty(value = "URL (온라인)")
    String onlineUrl;
    @ApiModelProperty(value = "모임 상태")
    PartyStateType partyStateType;
    @ApiModelProperty(value = "주최자 기본키")
    Long hostUserPriKey;
    @ApiModelProperty(value = "주최자 이름")
    String hostUserName;
    @ApiModelProperty(value = "주최자 사진")
    String hostUserPicture;
    @ApiModelProperty(value = "주최자 레벨")
    Double hostLevel;
    @ApiModelProperty(value = "모임 사진 URL")
    String pictureUrl;
    @ApiModelProperty(value = "모임 태그 기본키")
    Long tagPriKey;
    @ApiModelProperty(value = "모임 태그 이름")
    String tagName;
    @ApiModelProperty(value = "모임 생성 일시")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "모임 수정 일시")
    LocalDateTime modifiedAt;
    @ApiModelProperty(value = "완료 했거나 확정된 인원")
    Long confirmCnt;
    @ApiModelProperty(value = "모임 신고 횟수")
    Long warningCnt;
    @ApiModelProperty(value = "유저 기준 검색 시, 모임에 대한 유저의 상태")
    GuestType guestType;

    public static PartyRes from(
            PartyDto partyDto
    ) {
        return new PartyRes(
                partyDto.getId(),
                partyDto.getName(),
                partyDto.getMeetingDay(),
                partyDto.getPersonnel(),
                partyDto.getIntroStr(),
                partyDto.getPartyType(),
                partyDto.getPartyPlace(),
                partyDto.getContactType(),
                partyDto.getUseProgram(),
                partyDto.getOnlineUrl(),
                partyDto.getPartyStateType(),
                partyDto.getHostUserPriKey(),
                partyDto.getHostUserName(),
                partyDto.getHostFileName() == null ||
                        partyDto.getHostFileName().isBlank() ?
                        null : FileUrl.FILE_DOWN_URL.getUrl() + partyDto.getHostFileName(),
                partyDto.getHostLevel(),
                partyDto.getFileBaseNm() == null ||
                        partyDto.getFileBaseNm().isBlank() ?
                        null : FileUrl.FILE_DOWN_URL.getUrl() + partyDto.getFileBaseNm(),
                partyDto.getTagPriKey(),
                partyDto.getTagName(),
                partyDto.getCreatedAt(),
                partyDto.getModifiedAt(),
                partyDto.getConfirmCnt(),
                partyDto.getWarningCnt(),
                partyDto.getGuestType()
        );
    }
}
