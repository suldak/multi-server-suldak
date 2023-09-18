package com.sulsul.suldaksuldak.repo.tag.taste;

import com.sulsul.suldaksuldak.dto.tag.TasteTypeDto;

import java.util.List;

public interface TasteTypeRepositoryCustom {
    List<TasteTypeDto> findByLiquorPriKey(Long liquorPriKey);
}
