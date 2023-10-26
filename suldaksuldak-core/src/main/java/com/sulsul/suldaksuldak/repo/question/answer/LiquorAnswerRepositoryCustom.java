package com.sulsul.suldaksuldak.repo.question.answer;

import com.sulsul.suldaksuldak.dto.question.LiquorAnswerDto;

import java.util.List;

public interface LiquorAnswerRepositoryCustom {
    List<LiquorAnswerDto> findByQuestionPriKey(Long questionPriKey);
}
