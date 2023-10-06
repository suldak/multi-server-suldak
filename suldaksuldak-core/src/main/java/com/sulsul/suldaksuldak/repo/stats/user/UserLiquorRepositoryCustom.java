package com.sulsul.suldaksuldak.repo.stats.user;

import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;

import java.util.List;
import java.util.Optional;

public interface UserLiquorRepositoryCustom {
    Optional<UserLiquorDto> findByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    );

    /**
     * 유저가 많이 조회한 상위 N개의 술 조회
     */
    List<Long> findRatingByUserId(
            Long userPriKey,
            Integer limitNum
    );
}
