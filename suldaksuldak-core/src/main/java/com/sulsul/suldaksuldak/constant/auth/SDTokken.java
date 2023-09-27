package com.sulsul.suldaksuldak.constant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SDTokken {
//    REFRESH_HEADER("Refresh"),
    REFRESH_HEADER("Authorization"),
    TOKEN_TYPE("BEARER"),
    ACCESS_KEY_HEADER("AccessKey"),
//    ReqUserNm("UserNm"),
//    ReqUserPriKey("ReqUserPriKey")
    REQ_COMPANY_ID_LIST("CompanyIdList")
    ;
    private final String text;
}
