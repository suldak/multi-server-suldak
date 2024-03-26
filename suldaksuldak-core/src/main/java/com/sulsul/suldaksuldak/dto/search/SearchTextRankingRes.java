package com.sulsul.suldaksuldak.dto.search;

import lombok.Value;

@Value
public class SearchTextRankingRes {
    Integer ranking;
    String text;
    Boolean isNew;
    Boolean isDown;
    Boolean isUp;

    public static SearchTextRankingRes of (
            Integer ranking,
            SearchTextRankingDto searchTextRankingDto,
            Boolean isNew,
            Boolean isDown,
            Boolean isUp
    ) {
        return new SearchTextRankingRes(
                ranking,
                searchTextRankingDto.getSearchText(),
                isNew,
                isDown,
                isUp
        );
    }
}
