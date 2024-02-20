package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;

import java.util.List;

public interface LiquorSnackRepositoryCustom {
    List<LiquorSnackDto> findAllLiquorSnack();
    List<LiquorSnackDto> findByLiquorPriKey(Long liquorPriKey);
}
