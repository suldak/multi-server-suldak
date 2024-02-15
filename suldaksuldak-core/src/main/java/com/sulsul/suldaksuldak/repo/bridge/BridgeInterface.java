package com.sulsul.suldaksuldak.repo.bridge;

import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BridgeInterface {
    Optional<BridgeDto> findByLiquorPriKeyAndTagPriKey(
            Long liquorPriKey,
            Long tagPriKey
    );

    @Transactional
    Boolean deleteByLiquorPriKey(
            Long liquorPriKey
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
