package com.sulsul.suldaksuldak.dto.party.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "모임 댓글 Request")
public class PartyCommentReq {
    @ApiModelProperty(value = "댓글 내용", required = true)
    String comment;
    @ApiModelProperty(value = "대댓글을 달 댓글의 기본키")
    String commentPriKey;
}
