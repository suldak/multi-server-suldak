package com.sulsul.suldaksuldak.Service.question;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.dto.question.AnswerWeightDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.question.answer.LiquorAnswerRepository;
import com.sulsul.suldaksuldak.repo.question.weight.AnswerWeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorAnswerWeightService {
    private final LiquorAnswerRepository liquorAnswerRepository;
    private final AnswerWeightRepository answerWeightRepository;

    public Boolean createAnswerWeight(
            AnswerWeightDto answerWeightDto
    ) {
        try {
            if (answerWeightDto.getId() == null) {
                Optional<LiquorAnswer> liquorAnswer =
                        liquorAnswerRepository.findById(answerWeightDto.getLiquorAnswerId());
                if (liquorAnswer.isEmpty())
                    throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND LiquorAnswer");
                answerWeightRepository.save(
                        answerWeightDto.toEntity(liquorAnswer.get())
                );
            } else {
                answerWeightRepository.findById(answerWeightDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    answerWeightRepository.save(
                                            answerWeightDto.updateEntity(
                                                    findEntity
                                            )
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "NOT FOUND LiquorAnswer"
                                    );
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    public List<AnswerWeightDto> getByLiquorAnswerPriKey(
            Long liquorAnswerPriKey
    ) {
        try {
            if (liquorAnswerPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "LiquorAnswerPriKey NULL");
            return answerWeightRepository.findByLiquorAnswerPriKey(liquorAnswerPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean deleteAnswerWeight(
            Long priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "PriKey NULL");
            answerWeightRepository.deleteById(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
