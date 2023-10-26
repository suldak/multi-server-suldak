package com.sulsul.suldaksuldak.constant.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagType {
    DRINKING_CAPACITY("주량"),
    LIQUOR_ABV("도수"),
    LIQUOR_DETAIL("2차 분류"),
    LIQUOR_MATERIAL("재료"),
    LIQUOR_NAME("1차 분류"),
    LIQUOR_SELL("판매처"),
    STATE_TYPE("상태"),
    TASTE_TYPE("맛"),
    LIQUOR_SNACK("안주");

    private final String text;
}
