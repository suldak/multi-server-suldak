package com.sulsul.suldaksuldak.repo.liquor.detail;

import com.sulsul.suldaksuldak.dto.tag.LiquorDetailDto;

import java.util.Optional;

public interface LiquorDetailRepositoryCustom {
    Optional<LiquorDetailDto> findByPriKey(Long priKey);
}