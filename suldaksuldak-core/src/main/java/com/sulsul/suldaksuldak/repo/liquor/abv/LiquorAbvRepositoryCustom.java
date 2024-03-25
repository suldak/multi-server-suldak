package com.sulsul.suldaksuldak.repo.liquor.abv;

import com.sulsul.suldaksuldak.dto.tag.LiquorAbvDto;

import java.util.List;
import java.util.Optional;

public interface LiquorAbvRepositoryCustom {
    Optional<LiquorAbvDto> findByPriKey(Long priKey);
    List<LiquorAbvDto> findByPriKeyList(List<Long> priKeyList);
}
