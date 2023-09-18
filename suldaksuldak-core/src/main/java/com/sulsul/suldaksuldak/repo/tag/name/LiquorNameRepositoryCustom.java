package com.sulsul.suldaksuldak.repo.tag.name;

import com.sulsul.suldaksuldak.dto.tag.LiquorNameDto;

import java.util.Optional;

public interface LiquorNameRepositoryCustom {
    Optional<LiquorNameDto> findByPriKey(Long priKey);
}
