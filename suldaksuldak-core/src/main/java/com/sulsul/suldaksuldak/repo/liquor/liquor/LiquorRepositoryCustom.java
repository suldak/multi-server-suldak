package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
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

    Page<LiquorTotalRes> findByTags(
            Pageable pageable,
            List<Long> snackPriKeys,
            List<Long> sellPriKeys,
            List<Long> materialPriKeys,
            List<Long> statePriKeys,
            List<Long> tastePriKeys,
            List<Long> liquorAbvPriKeys,
            List<Long> liquorDetailPriKeys,
            List<Long> drinkingCapacityPriKeys,
            List<Long> liquorNamePriKeys,
            String searchTag
    );
}
