package com.sulsul.suldaksuldak.dto.report.party;

import com.sulsul.suldaksuldak.domain.report.ReportPartyReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 신고 이유 Request")
public class ReportPartyReasonDto {
    @ApiModelProperty(value = "모임 신고 이유 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "신고 이유", required = true)
    String reason;

    public static ReportPartyReasonDto of (
            ReportPartyReason reportPartyReason
    ) {
        return new ReportPartyReasonDto(
                reportPartyReason.getId(),
                reportPartyReason.getReason()
        );
    }
}
