package com.sulsul.suldaksuldak.dto.stats.search;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LiquorSearchLogDto {
    Long id;
    Long liquorId;
    LocalDateTime searchAt;

    public static LiquorSearchLogDto of (
            Long id,
            Long liquorId
    ) {
        return new LiquorSearchLogDto(
                id,
                liquorId,
                null
        );
    }

    public LiquorSearchLog toEntity(
            Liquor liquor
    ) {
        return LiquorSearchLog.of(
                null,
                liquor
        );
    }
}
