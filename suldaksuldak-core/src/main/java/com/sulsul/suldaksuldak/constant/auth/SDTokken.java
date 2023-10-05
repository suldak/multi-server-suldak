package com.sulsul.suldaksuldak.constant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SDTokken {
//    REFRESH_HEADER("Refresh"),
    REFRESH_HEADER("Authorization"),
    TOKEN_TYPE("Bearer"),
    ACCESS_KEY_HEADER("AccessKey"),
//    ReqUserNm("UserNm"),
//    ReqUserPriKey("ReqUserPriKey")
    USER_PRI_KEY("userPriKey")
    ;
    private final String text;
}
