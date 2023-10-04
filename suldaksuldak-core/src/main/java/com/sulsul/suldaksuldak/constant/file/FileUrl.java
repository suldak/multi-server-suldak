package com.sulsul.suldaksuldak.constant.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileUrl {
    FILE_DOWN_URL("/api/file/download/");

    private final String url;
}
