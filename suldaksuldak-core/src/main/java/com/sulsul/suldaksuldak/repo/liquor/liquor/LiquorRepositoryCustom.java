package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;

import java.util.List;
import java.util.Optional;

public interface LiquorRepositoryCustom {
    Optional<LiquorDto> findByPriKey(Long priKey);
    List<Long> findByLiquorAbvPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    );
    List<Long> findByLiquorDetailPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    );
    List<Long> findByDrinkingCapacityPriKey(
            List<Long> liquorPriKeys,
            Long priKey
    );
    List<Long> findByLiquorNamePriKey(
            List<Long> liquorPriKeys,
            Long priKey
    );
    List<Long> findBySearchTag(
            String searchTag
    );
}
