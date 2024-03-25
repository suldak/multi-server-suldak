package com.sulsul.suldaksuldak.repo.tag.sell;

import com.sulsul.suldaksuldak.dto.tag.LiquorSellDto;

import java.util.List;

public interface LiquorSellRepositoryCustom {
    List<LiquorSellDto> findByLiquorPriKey(Long liquorPriKey);
    List<LiquorSellDto> findByPriKeyList(List<Long> priKeyList);
}
