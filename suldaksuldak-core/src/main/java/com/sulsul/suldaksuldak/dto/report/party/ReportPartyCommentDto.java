package com.sulsul.suldaksuldak.dto.report.party;

import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.user.User;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReportPartyCommentDto {
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

    public ReportPartyCommentDto(
            Long id,
            Long userPriKey,
            String userNickname,
            String userFileNm,
            Party party,
            String commentPriKey,
            String comment,
            User user,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.userPriKey = userPriKey;
        this.userNickname = userNickname;
        this.userFileNm = userFileNm;
        this.partyPriKey = party.getId();
        this.partyName = party.getName();
        this.partyFileNm = party.getFileBase() == null ? null : party.getFileBase().getFileNm();
        this.commentPriKey = commentPriKey;
        this.comment = comment;
        this.commentUserPriKey = user.getId();
        this.commentUserNickname = user.getNickname();
        this.commentUserFileNm = user.getFileBase() == null ? null : user.getFileBase().getFileNm();
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
