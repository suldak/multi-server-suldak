package com.sulsul.suldaksuldak.repo.tag.abv;

import com.sulsul.suldaksuldak.dto.tag.LiquorAbvDto;

import java.util.Optional;

public interface LiquorAbvRepositoryCustom {
    Optional<LiquorAbvDto> findByPriKey(Long priKey);
}
