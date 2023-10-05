package com.sulsul.suldaksuldak.dto.cut;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@ApiModel(value = "차단 유저 Response")
public class CutOffUserRes {
    @ApiModelProperty(value = "기본키")
    Long id;
    @ApiModelProperty(value = "해당 유저의 기본키")
    Long userId;
    @ApiModelProperty(value = "차단 당한 유저의 기본키")
    Long cutUserId;
    @ApiModelProperty(value = "차단 당한 유저의 닉네임")
    String cutUserNickname;
    @ApiModelProperty(value = "차단 당한 유저의 프로필 사진")
    String cutUserPictureUrl;
    @ApiModelProperty(value = "생성 일시")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "수정 일시")
    LocalDateTime modifiedAt;

    public static CutOffUserRes from (
            CutOffUserDto cutOffUserDto
    ) {
        return new CutOffUserRes(
                cutOffUserDto.getId(),
                cutOffUserDto.getUserId(),
                cutOffUserDto.getCutUserId(),
                cutOffUserDto.getCutUserNickname(),
                cutOffUserDto.getCutUserFileNm() == null || cutOffUserDto.getCutUserFileNm().isBlank() ?
                null : FileUrl.FILE_DOWN_URL.getUrl() + cutOffUserDto.getCutUserFileNm(),
                cutOffUserDto.getCreatedAt(),
                cutOffUserDto.getModifiedAt()
        );
    }
}
