package com.sulsul.suldaksuldak.dto.admin.feedback;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserPartyFeedbackRes {
    Long id;
    FeedbackType feedbackType;
    String comment;
    Long partyPriKey;
    String partyName;
    String partyFileNm;
    Long writerPriKey;
    String writerNickname;
    String writerFileNm;
    Long targetUserPriKey;
    String targetUserNickname;
    String targetUserFileNm;
    LocalDateTime feedbackAt;

    public static UserPartyFeedbackRes from (
            UserPartyFeedbackDto dto
    ) {
        return new UserPartyFeedbackRes(
                dto.getId(),
                dto.getFeedbackType(),
                dto.getComment(),
                dto.getPartyPriKey(),
                dto.getPartyName(),
                dto.getPartyFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.name() + dto.getPartyFileNm(),
                dto.getWriterPriKey(),
                dto.getWriterNickname(),
                dto.getWriterFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + dto.getWriterFileNm(),
                dto.getTargetUserPriKey(),
                dto.getTargetUserNickname(),
                dto.getTargetUserFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + dto.getTargetUserFileNm() ,
                dto.getFeedbackAt()
        );
    }
}
