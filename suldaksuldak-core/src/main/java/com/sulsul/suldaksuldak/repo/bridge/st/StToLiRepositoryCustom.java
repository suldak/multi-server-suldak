package com.sulsul.suldaksuldak.repo.bridge.st;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.Optional;

public interface StToLiRepositoryCustom {
    Optional<BridgeDto> findByLiquorPriKeyAndStatePriKey(
            Long liquorPriKey,
            Long stateTypePriKey
    );
}
