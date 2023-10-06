package com.sulsul.suldaksuldak.repo.stats.user;

import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;

import java.util.List;

public interface UserLiquorRepositoryCustom {
    List<UserLiquorDto> findRatingByUserId(Long userPriKey);
}
