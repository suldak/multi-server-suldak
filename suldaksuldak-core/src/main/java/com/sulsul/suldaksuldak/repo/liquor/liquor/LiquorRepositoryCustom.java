package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LiquorRepositoryCustom {
    Optional<LiquorDto> findByPriKey(Long priKey);
    List<Long> findByLiquorAbvPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
    List<Long> findByLiquorDetailPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
    List<Long> findByDrinkingCapacityPriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
    List<Long> findByLiquorNamePriKey(
            List<Long> liquorPriKeys,
            List<Long> tagPriKeys
    );
    List<Long> findBySearchTag(
            String searchTag
    );
    Page<Long> findByCreatedLatest(Pageable pageable);
    Page<LiquorDto> findByLiquorPriKeyListAndSearchTag(
            Pageable pageable,
            List<Long> liquorPriKeyList
    );
    List<Long> findAllLiquorPriKey();
}
