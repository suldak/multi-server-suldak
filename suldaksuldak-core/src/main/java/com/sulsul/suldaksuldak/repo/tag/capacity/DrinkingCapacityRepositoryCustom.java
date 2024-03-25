package com.sulsul.suldaksuldak.repo.tag.capacity;

import com.sulsul.suldaksuldak.dto.tag.DrinkingCapacityDto;

import java.util.List;
import java.util.Optional;

public interface DrinkingCapacityRepositoryCustom {
    Optional<DrinkingCapacityDto> findByPriKey(Long priKey);
    List<DrinkingCapacityDto> findByPriKeyList(List<Long> priKeyList);
}
