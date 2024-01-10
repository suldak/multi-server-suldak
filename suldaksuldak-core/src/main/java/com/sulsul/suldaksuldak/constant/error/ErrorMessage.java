package com.sulsul.suldaksuldak.constant.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_USER("유처를 찾지 못하였습니다."),
    NOT_FOUND_LIQUOR_DATA("술 데이터를 찾지 못하였습니다."),
    NOT_USER("ID 또는 PW가 맞지 않습니다."),
    NOT_FOUND_USER_PRI_KEY_FROM_TOKEN("Token 정보가 유효하지 않습니다."),
    NOT_FOUND_USER_PRI_KEY("유저의 기본키를 찾지 못하였습니다.")
    ;
    private final String message;
}
