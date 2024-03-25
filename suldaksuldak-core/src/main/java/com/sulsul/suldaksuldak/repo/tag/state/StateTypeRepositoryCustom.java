package com.sulsul.suldaksuldak.repo.tag.state;

import com.sulsul.suldaksuldak.dto.tag.StateTypeDto;

import java.util.List;

public interface StateTypeRepositoryCustom {
    List<StateTypeDto> findByLiquorPriKey(Long liquorPriKey);
    List<StateTypeDto> findByPriKeyList(List<Long> priKeyList);
}
