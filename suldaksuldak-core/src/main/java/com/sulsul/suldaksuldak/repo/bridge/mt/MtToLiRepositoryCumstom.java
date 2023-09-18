package com.sulsul.suldaksuldak.repo.bridge.mt;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.Optional;

public interface MtToLiRepositoryCumstom {
    Optional<BridgeDto> findByLiquorPriKeyAndLiquorMaterialPriKey(
            Long liquorPriKey,
            Long liquorMaterialPriKey
    );
}
