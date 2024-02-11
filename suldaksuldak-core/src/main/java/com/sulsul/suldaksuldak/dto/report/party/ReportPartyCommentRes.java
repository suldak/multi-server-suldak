package com.sulsul.suldaksuldak.dto.report.party;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "모임 댓글 신고 Res")
public class ReportPartyCommentRes {
    @ApiModelProperty(value = "모임 댓글 신고 기본키")
    Long id;
    @ApiModelProperty(value = "모임 댓글을 신고한 유저의 기본키")
    Long userPriKey;
    @ApiModelProperty(value = "모임 댓글을 신고한 유저의 닉네임")
    String userNickname;
    @ApiModelProperty(value = "모임 댓글을 신고한 유저의 프로필 사진 URL")
    String userFileNm;
    @ApiModelProperty(value = "신고당한 모임이 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "신고당한 모임의 이름")
    String partyName;
    @ApiModelProperty(value = "신고당한 모임의 썸네일 사진 URL")
    String partyFileNm;
    @ApiModelProperty(value = "신고당한 모임 댓글의 기본키")
    String commentPriKey;
    @ApiModelProperty(value = "신고당한 모임 댓글의 내용")
    String comment;
    @ApiModelProperty(value = "신고당한 모임 댓글의 작성자 기본키")
    Long commentUserPriKey;
    @ApiModelProperty(value = "신고당한 모임 댓글의 작성자 닉네임")
    String commentUserNickname;
    @ApiModelProperty(value = "신고당한 모임 댓글의 작성자 프로필 사진 URL")
    String commentUserFileNm;
    @ApiModelProperty(value = "신고당한 시간")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "해당 데이터의 수정한 시간")
    LocalDateTime modifiedAt;

    public static ReportPartyCommentRes from (
            ReportPartyCommentDto reportPartyCommentDto
    ) {
        return new ReportPartyCommentRes(
                reportPartyCommentDto.getId(),
                reportPartyCommentDto.getUserPriKey(),
                reportPartyCommentDto.getUserNickname(),
                reportPartyCommentDto.getUserFileNm(),
                reportPartyCommentDto.getPartyPriKey(),
                reportPartyCommentDto.getPartyName(),
                reportPartyCommentDto.getPartyFileNm(),
                reportPartyCommentDto.getCommentPriKey(),
                reportPartyCommentDto.getComment(),
                reportPartyCommentDto.getCommentUserPriKey(),
                reportPartyCommentDto.getCommentUserNickname(),
                reportPartyCommentDto.getCommentUserFileNm(),
                reportPartyCommentDto.getCreatedAt(),
                reportPartyCommentDto.getModifiedAt()
        );
    }
}
