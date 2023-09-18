package com.sulsul.suldaksuldak.repo.bridge.tt;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.Optional;

public interface TtToLiRepositoryCustom {
    Optional<BridgeDto> findByLiquorPriKeyAndTastePriKey(
            Long liquorPriKey,
            Long tastePriKey
    );
}
