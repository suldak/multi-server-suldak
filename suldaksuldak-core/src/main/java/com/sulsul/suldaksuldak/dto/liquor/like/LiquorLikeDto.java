package com.sulsul.suldaksuldak.dto.liquor.like;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorLikeDto {
    Long id;
    Long liquorPriKey;
    String liquorName;
    String liquorFileNm;
    Long userPriKey;
    String userNickname;
    String userFileNm;
    LocalDateTime likeTime;
}
