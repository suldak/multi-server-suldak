package com.sulsul.suldaksuldak.repo.stats.user;

import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;

import java.util.List;
import java.util.Optional;

public interface UserLiquorRepositoryCustom {
    Optional<UserLiquorDto> findByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    );
    List<UserLiquorDto> findRatingByUserId(Long userPriKey);
}
