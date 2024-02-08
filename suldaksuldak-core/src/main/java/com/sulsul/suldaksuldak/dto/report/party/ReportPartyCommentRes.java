package com.sulsul.suldaksuldak.dto.report.party;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReportPartyCommentRes {
    Long id;
    Long userPriKey;
    String userNickname;
    String userFileNm;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    String commentPriKey;
    String comment;
    Long commentUserPriKey;
    String commentUserNickname;
    String commentUserFileNm;
    LocalDateTime createdAt;
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
