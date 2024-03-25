package com.sulsul.suldaksuldak.repo.liquor.name;

import com.sulsul.suldaksuldak.dto.tag.LiquorNameDto;

import java.util.List;
import java.util.Optional;

public interface LiquorNameRepositoryCustom {
    Optional<LiquorNameDto> findByPriKey(Long priKey);
    List<LiquorNameDto> findByPriKeyList(List<Long> priKeyList);
}
