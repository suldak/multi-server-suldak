package com.sulsul.suldaksuldak.repo.question.weight;

import com.sulsul.suldaksuldak.dto.question.AnswerWeightDto;

import java.util.List;

public interface AnswerWeightRepositoryCustom {
    List<AnswerWeightDto> findByLiquorAnswerPriKey(
            Long liquorAnswerPriKey
    );
}
