package com.sulsul.suldaksuldak.dto.report.party;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "모임 신고 Res")
public class ReportPartyRes {
    @ApiModelProperty(value = "모임 신고 기본키")
    Long id;
    @ApiModelProperty(value = "신고한 유저의 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "신고한 유저의 이름")
    String userNickname;
    @ApiModelProperty(value = "신고한 유저의 프로필 사진")
    String userFileUrl;
    @ApiModelProperty(value = "모임 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "모임 이름")
    String partyName;
    @ApiModelProperty(value = "모임 사진")
    String partyFileUrl;
    @ApiModelProperty(value = "모임 신고 이유 기본키")
    Long reasonPriKey;
    @ApiModelProperty(value = "모임 신고 이유")
    String reason;
    @ApiModelProperty(value = "모임 신고 처리 여부")
    Boolean complete;
    @ApiModelProperty(value = "모임 신고 생성 일자")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "모임 신고 수정 일자")
    LocalDateTime modifiedAt;

    public static ReportPartyRes from (
            ReportPartyDto reportPartyDto
    ) {
        return new ReportPartyRes(
                reportPartyDto.getId(),
                reportPartyDto.getUserPriKey(),
                reportPartyDto.getUserNickname(),
                reportPartyDto.getUserFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + reportPartyDto.getUserFileNm(),
                reportPartyDto.getPartyPriKey(),
                reportPartyDto.getPartyName(),
                reportPartyDto.getPartyFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + reportPartyDto.getPartyFileNm(),
                reportPartyDto.getReasonPriKey(),
                reportPartyDto.getReason(),
                reportPartyDto.getComplete(),
                reportPartyDto.getCreatedAt(),
                reportPartyDto.getModifiedAt()
        );
    }
}
