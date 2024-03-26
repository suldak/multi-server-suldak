package com.sulsul.suldaksuldak.repo.search.recommend;

import com.sulsul.suldaksuldak.dto.search.RecommendSearchTextDto;

import java.util.List;

public interface RecommendSearchTextRepositoryCustom {
    List<RecommendSearchTextDto> findByOption(
            Boolean isActive,
            Integer limit
    );
}
