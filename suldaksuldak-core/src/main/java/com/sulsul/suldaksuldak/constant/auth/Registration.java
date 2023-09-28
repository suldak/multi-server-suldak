package com.sulsul.suldaksuldak.constant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Registration {
    KAKAO("KAKAO"),
    NAVER("NAVER"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE"),
    SULDAKSULDAK("SULDAKSULDAK")
    ;

    private final String str;
}
