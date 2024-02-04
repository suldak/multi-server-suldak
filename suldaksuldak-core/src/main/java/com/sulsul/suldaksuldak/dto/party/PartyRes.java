package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
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
    @ApiModelProperty(value = "주최자 기본키")
    Long hostUserPriKey;
    @ApiModelProperty(value = "주최자 이름")
    String hostUserName;
    @ApiModelProperty(value = "모임 사진 URL")
    String pictureUrl;
    @ApiModelProperty(value = "모임 생성 일시")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "모임 수정 일시")
    LocalDateTime modifiedAt;

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
                partyDto.getHostUserPriKey(),
                partyDto.getHostUserName(),
                partyDto.getFileBaseNm() == null ||
                        partyDto.getFileBaseNm().isBlank() ?
                        null : FileUrl.FILE_DOWN_URL.getUrl() + partyDto.getFileBaseNm(),
                partyDto.getCreatedAt(),
                partyDto.getModifiedAt()
        );
    }
}
