package com.sulsul.suldaksuldak.repo.liquor.like;

import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LiquorLikeRepositoryCustom {
    Optional<LiquorLike> findByByUserPriKeyAndLiquorPriKey(
            Long userPriKey,
            Long liquorPriKey
    );

    Page<LiquorLikeDto> findByLiquorPriKeyWithUserPriKey(
            Long liquorPriKey,
            Long userPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    );
}
