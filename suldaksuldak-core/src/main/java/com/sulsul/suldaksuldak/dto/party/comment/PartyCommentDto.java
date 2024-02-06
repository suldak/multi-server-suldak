package com.sulsul.suldaksuldak.dto.party.comment;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PartyCommentDto {
    String id;
    String comment;
    Long userPriKey;
    String userNickname;
    String userFileNm;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    String groupComment;
//    Long commentCnt;
    Integer commentDep;
    Boolean isDelete;
    Boolean isModified;
    Integer warningCnt;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
}
