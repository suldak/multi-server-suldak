package com.sulsul.suldaksuldak.repo.bridge;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;

import java.util.List;
import java.util.Optional;

public interface BridgeInterface {
    Optional<BridgeDto> findByLiquorPriKeyAndTagPriKey(
            Long liquorPriKey,
            Long tagPriKey
    );

    List<Long> findLiquorPriKeyByTagPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );

    List<Long> findLiquorPriKeyByTagPriKey(
//            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
}
