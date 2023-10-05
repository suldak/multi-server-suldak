package com.sulsul.suldaksuldak.repo.cut;

import com.sulsul.suldaksuldak.dto.cut.CutOffUserDto;

import java.util.List;

public interface CutOffUserRepositoryCustom {
    List<CutOffUserDto> findByUserId(Long userId);
    List<Long> findByUserIdAndCutUserId(Long userId, Long cutUserId);
    List<Long> findCutUserIdByUserId(Long userId);
}
