package com.sulsul.suldaksuldak.dto.search;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class SearchTextDto {
    Long userPriKey;
    String nickname;
    String searchText;
    LocalDateTime searchAt;
}
