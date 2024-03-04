package com.sulsul.suldaksuldak.dto.party.cancel;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "모임 취소 Response")
public class PartyCancelRes {
    @ApiModelProperty(value = "모임 취소 기본키")
    Long id;
    @ApiModelProperty(value = "모임 취소 이유 기본키")
    Long partyCancelReasonPriKey;
    @ApiModelProperty(value = "모임 역할군")
    PartyRoleType partyRoleType;
    @ApiModelProperty(value = "모임 취소 이유의 텍스트")
    String partyCancelReason;
    @ApiModelProperty(value = "상세한 모임 취소 이유")
    String detailReason;
    @ApiModelProperty(value = "모임 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "모임 이름")
    String partyName;
    @ApiModelProperty(value = "모임 프로필 사진 URL")
    String partyPictureUrl;
    @ApiModelProperty(value = "취소한 맴버 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "취소한 맴버 닉네임")
    String userNickname;
    @ApiModelProperty(value = "취소한 맴버 사진 URL")
    String userPictureUrl;

    public static PartyCancelRes from(
            PartyCancelDto partyCancelDto
    ) {
        return new PartyCancelRes(
                partyCancelDto.getId(),
                partyCancelDto.getPartyCancelReasonPriKey(),
                partyCancelDto.getPartyRoleType(),
                partyCancelDto.getPartyCancelReason(),
                partyCancelDto.getDetailReason(),
                partyCancelDto.getPartyPriKey(),
                partyCancelDto.getPartyName(),
                partyCancelDto.getPartyFileNm() == null ?
                null : FileUrl.FILE_DOWN_URL.name() + partyCancelDto.getPartyFileNm(),
                partyCancelDto.getUserPriKey(),
                partyCancelDto.getUserNickname(),
                partyCancelDto.getUserFileNm() == null ?
                null : FileUrl.FILE_DOWN_URL.name() + partyCancelDto.getUserFileNm()
        );
    }
}
