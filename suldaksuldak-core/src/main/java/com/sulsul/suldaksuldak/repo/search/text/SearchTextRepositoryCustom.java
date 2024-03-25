package com.sulsul.suldaksuldak.repo.search.text;

import com.sulsul.suldaksuldak.dto.search.SearchTextDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchTextRepositoryCustom {
    List<SearchTextDto> findListByOption(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Long userPriKey,
            Integer limitNum
    );
}
