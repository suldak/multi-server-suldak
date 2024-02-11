package com.sulsul.suldaksuldak.dto.party.comment;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@ApiModel(value = "모임 댓글 Res")
public class PartyCommentRes {
    @ApiModelProperty(value = "댓글 기본키")
    String id;
    @ApiModelProperty(value = "댓글 내용")
    String comment;
    @ApiModelProperty(value = "댓글 작성자")
    Long userPriKey;
    @ApiModelProperty(value = "댓글 작성자 기본키")
    String userNickname;
    @ApiModelProperty(value = "댓글 작성장 프로필 사진 URL")
    String userFileNm;
    @ApiModelProperty(value = "댓글이 작성된 모임 기본키")
    Long partyPriKey;
    @ApiModelProperty(value = "댓글이 작성된 모임 이름")
    String partyName;
    @ApiModelProperty(value = "댓글이 작성된 모임 썸네일 URL")
    String partyFileNm;
    @ApiModelProperty(value = "댓글 Group (대댓글)")
    String groupComment;
    @ApiModelProperty(value = "댓글 Dep (대댓글)")
    Integer commentDep;
    @ApiModelProperty(value = "댓글 삭제 여부")
    Boolean isDelete;
    @ApiModelProperty(value = "댓글 수정 여부")
    Boolean isModified;
    @ApiModelProperty(value = "댓글 신고 개수")
    Long warningCnt;
    @ApiModelProperty(value = "댓글 생성 시간")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "댓글 수정 시간")
    LocalDateTime modifiedAt;
    @ApiModelProperty(value = "대댓글 모음")
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
                partyCommentDto.getCommentDep(),
                partyCommentDto.getIsDelete(),
                partyCommentDto.getIsModified(),
                partyCommentDto.getWarningCnt(),
                partyCommentDto.getCreatedAt(),
                partyCommentDto.getModifiedAt(),
                partyCommentDtos
        );
    }

    public static PartyCommentRes from (
            PartyCommentDto partyCommentDto
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
                partyCommentDto.getCommentDep(),
                partyCommentDto.getIsDelete(),
                partyCommentDto.getIsModified(),
                partyCommentDto.getWarningCnt(),
                partyCommentDto.getCreatedAt(),
                partyCommentDto.getModifiedAt(),
                List.of()
        );
    }
}
