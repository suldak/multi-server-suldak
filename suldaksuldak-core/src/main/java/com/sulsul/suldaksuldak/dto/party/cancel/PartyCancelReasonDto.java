package com.sulsul.suldaksuldak.dto.party.cancel;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancelReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 취소 이유 Request")
public class PartyCancelReasonDto {
    @ApiModelProperty(value = "모임 취소 이유 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "모임 취소 이유")
    String reason;
    @ApiModelProperty(value = "모임 취소 이유 역할 타입 (HOST / GUEST)")
    PartyRoleType partyRoleType;

    public static PartyCancelReasonDto of (
            PartyCancelReason partyCancelReason
    ) {
        return new PartyCancelReasonDto(
                partyCancelReason.getId(),
                partyCancelReason.getReason(),
                partyCancelReason.getPartyRoleType()
        );
    }
}
