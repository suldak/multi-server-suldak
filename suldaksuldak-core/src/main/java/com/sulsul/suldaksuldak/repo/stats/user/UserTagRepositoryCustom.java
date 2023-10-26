package com.sulsul.suldaksuldak.repo.stats.user;

import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.dto.stats.user.UserTagDto;

import java.util.Optional;

public interface UserTagRepositoryCustom {
    Optional<UserTagDto> findByUserPriKeyAndTagTypeAndTagId(
            Long userPriKey,
            TagType tagType,
            Long tagId
    );
}
