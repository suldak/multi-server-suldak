package com.sulsul.suldaksuldak.repo.question.question;

import com.sulsul.suldaksuldak.dto.question.LiquorQuestionDto;

import java.util.List;
import java.util.Optional;

public interface LiquorQuestionRepositoryCustom {
    Optional<LiquorQuestionDto> findByPriKey(Long priKey);
    Optional<LiquorQuestionDto> findByQIndex(Integer qIndex);
    List<LiquorQuestionDto> findAllQuestion();
}
