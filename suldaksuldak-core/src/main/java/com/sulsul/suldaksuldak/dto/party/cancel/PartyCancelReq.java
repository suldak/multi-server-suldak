package com.sulsul.suldaksuldak.dto.party.cancel;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 취소 Request")
public class PartyCancelReq {
    @ApiModelProperty(value = "모임 취소 이유 기본키", required = true)
    Long partyCancelReasonPriKey;
    @ApiModelProperty(value = "모임 취소 상세 이유")
    String detailReason;

    public PartyCancelDto toDto(
            Long partyPriKey,
            Long userPriKey
    ) {
        return PartyCancelDto.of(
                partyCancelReasonPriKey,
                detailReason,
                partyPriKey,
                userPriKey
        );
    }
}
