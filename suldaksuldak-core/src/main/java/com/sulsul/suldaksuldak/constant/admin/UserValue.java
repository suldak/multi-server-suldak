package com.sulsul.suldaksuldak.constant.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserValue {
    DEFAULT_LEVEL(25.0)
    ;
    private final Double value;
}
