package com.sulsul.suldaksuldak.repo.liquor.like;

import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;

import java.util.Optional;

public interface LiquorLikeRepositoryCustom {
    Optional<LiquorLike> findByByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    );
}
