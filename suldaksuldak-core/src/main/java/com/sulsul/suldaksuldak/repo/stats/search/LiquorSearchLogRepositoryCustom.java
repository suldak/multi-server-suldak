package com.sulsul.suldaksuldak.repo.stats.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface LiquorSearchLogRepositoryCustom {
    Page<Long> findLiquorPriKeyByDateRange(
            Pageable pageable,
            LocalDateTime startAt,
            LocalDateTime endAt
    );
}
