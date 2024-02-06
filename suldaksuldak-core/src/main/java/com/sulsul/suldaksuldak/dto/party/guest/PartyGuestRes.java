package com.sulsul.suldaksuldak.dto.party.guest;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
@ApiModel(value = "모임 참가원 Response")
public class PartyGuestRes {
    @ApiModelProperty(value = "참가원 관리 Table 기본키")
    String id;
    @ApiModelProperty(value = "모임 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "모임 이름")
    String partyName;
    @ApiModelProperty(value = "참가원 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "참가원 닉네임")
    String userNickname;
    @ApiModelProperty(value = "유저 사진 URL")
    String pictureUrl;
    @ApiModelProperty(value = "확정 여부")
    GuestType confirmState;

    public static PartyGuestRes from(
            PartyGuestDto partyGuestDto
    ) {
        return new PartyGuestRes(
                partyGuestDto.getId(),
                partyGuestDto.getPartyPriKey(),
                partyGuestDto.getPartyName(),
                partyGuestDto.getUserPriKey(),
                partyGuestDto.getUserNickname(),
                partyGuestDto.getFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + partyGuestDto.getFileNm(),
                partyGuestDto.getConfirmState()
        );
    }
}
