package com.sulsul.suldaksuldak.dto.party.feedback;

import com.sulsul.suldaksuldak.domain.party.PartyFeedback;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 피드백 Request")
public class PartyFeedbackDto {
    @ApiModelProperty(value = "모임 피드백 기본키 (생략하면 생성)")
    Long id;
    @ApiModelProperty(value = "모임 피드백 내용", required = true)
    String feedBackText;
    @ApiModelProperty(value = "가감할 점수", required = true)
    Double score;

    public static PartyFeedbackDto of (
            PartyFeedback partyFeedback
    ) {
        return new PartyFeedbackDto(
                partyFeedback.getId(),
                partyFeedback.getFeedBackText(),
                partyFeedback.getScore()
        );
    }
}
