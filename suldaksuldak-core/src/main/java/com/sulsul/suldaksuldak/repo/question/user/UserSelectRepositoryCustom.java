package com.sulsul.suldaksuldak.repo.question.user;

import com.sulsul.suldaksuldak.dto.question.UserSelectDto;

import java.util.List;

public interface UserSelectRepositoryCustom {
    List<UserSelectDto> findByUserPriKey(Long userPriKey);

    List<UserSelectDto> findByUserPriKeyAndQuestionPriKey(
            Long userPriKey,
            Long questionPriKey
    );
}
