package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.sulsul.suldaksuldak.dto.bridge.SnToLiDto;

import java.util.List;
import java.util.Optional;

public interface SnToLiRepositoryCustom {
    Optional<SnToLiDto> findByLiquorPriKeyAndLiquorSnackPriKey(
            Long liquorPriKey,
            Long liquorSnackPriKey
    );
    List<SnToLiDto> findByLiquorSnackPriKey(
            Long liquorSnackPriKey
    );
    List<SnToLiDto> findByLiquorPriKey(
            Long liquorPriKey
    );
}
