package com.sulsul.suldaksuldak.repo.tag.material;

import com.sulsul.suldaksuldak.dto.tag.LiquorMaterialDto;

import java.util.List;

public interface LiquorMaterialRepositoryCustom {
    List<LiquorMaterialDto> findByLiquorPriKey(Long liquorPriKey);
}
