package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;

import java.util.Optional;

public interface LiquorRepositoryCustom {
    Optional<LiquorDto> findByPriKey(Long priKey);
}
