package com.sulsul.suldaksuldak.repo.liquor.detail;

import com.sulsul.suldaksuldak.dto.tag.LiquorDetailDto;

import java.util.List;
import java.util.Optional;

public interface LiquorDetailRepositoryCustom {
    Optional<LiquorDetailDto> findByPriKey(Long priKey);
    List<LiquorDetailDto>findByPriKeyList(List<Long> priKeyList);
}
