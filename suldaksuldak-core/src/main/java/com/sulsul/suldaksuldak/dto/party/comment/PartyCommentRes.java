package com.sulsul.suldaksuldak.dto.party.comment;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class PartyCommentRes {
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
    List<PartyCommentDto> childrenComment;

    public static PartyCommentRes from (
            PartyCommentDto partyCommentDto,
            List<PartyCommentDto> partyCommentDtos
    ) {
        return new PartyCommentRes(
                partyCommentDto.getId(),
                partyCommentDto.getComment(),
                partyCommentDto.getUserPriKey(),
                partyCommentDto.getUserNickname(),
                partyCommentDto.getUserFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + partyCommentDto.getUserFileNm(),
                partyCommentDto.getPartyPriKey(),
                partyCommentDto.getPartyName(),
                partyCommentDto.getPartyFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + partyCommentDto.getPartyFileNm(),
                partyCommentDto.getGroupComment(),
//                partyCommentDto.getCommentCnt(),
                partyCommentDto.getCommentDep(),
                partyCommentDto.getIsDelete(),
                partyCommentDto.getIsModified(),
                partyCommentDto.getWarningCnt(),
                partyCommentDto.getCreatedAt(),
                partyCommentDto.getModifiedAt(),
                partyCommentDtos
        );
    }
}
