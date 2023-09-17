package com.sulsul.suldaksuldak.repo.bridge.sell;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.Optional;

public interface SlToLiRepositoryCustom {
    Optional<BridgeDto> findByLiquorPriKeyAndLiquorSellPriKey(
            Long liquorPriKey,
            Long liquorSellPriKey
    );
}
