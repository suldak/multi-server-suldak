package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.List;
import java.util.Optional;

public interface SnToLiRepositoryCustom {
    Optional<BridgeDto> findByLiquorPriKeyAndLiquorSnackPriKey(
            Long liquorPriKey,
            Long liquorSnackPriKey
    );

    List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
}
