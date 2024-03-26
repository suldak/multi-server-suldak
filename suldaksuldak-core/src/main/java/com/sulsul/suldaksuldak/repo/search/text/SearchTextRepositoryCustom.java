package com.sulsul.suldaksuldak.repo.search.text;

import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.dto.search.SearchTextRankingDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchTextRepositoryCustom {
    List<SearchTextDto> findListByOption(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Long userPriKey,
            Integer limitNum
    );

    List<SearchTextRankingDto> findRankingList(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer limitNum
    );
}
