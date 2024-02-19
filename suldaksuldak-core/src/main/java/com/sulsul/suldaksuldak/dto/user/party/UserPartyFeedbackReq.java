package com.sulsul.suldaksuldak.dto.user.party;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 피드백 Req")
public class UserPartyFeedbackReq {
    List<FeedbackObj> feedbackObjs;

    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "유저에 대한 피드백 내용")
    public static class FeedbackObj {
        @ApiModelProperty(value = "상대방(User)의 기본키", required = true)
        Long userPriKey;
        @ApiModelProperty(value = "상대방에게 줄 점수", required = true)
        Double feedbackScore;
    }
}
