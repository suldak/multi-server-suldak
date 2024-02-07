package com.sulsul.suldaksuldak.dto.report.party;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 신고 Req")
public class ReportPartyReq {
    @ApiModelProperty(value = "모임 신고 이유 기본키", required = true)
    Long reasonPriKey;

    public ReportPartyDto toDto() {
        return ReportPartyDto.of(
                reasonPriKey
        );
    }
}
