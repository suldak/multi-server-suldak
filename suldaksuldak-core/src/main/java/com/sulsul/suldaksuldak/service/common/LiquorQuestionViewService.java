package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.question.LiquorAnswerDto;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionDto;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionTotalRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.question.answer.LiquorAnswerRepository;
import com.sulsul.suldaksuldak.repo.question.question.LiquorQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorQuestionViewService {
    private final LiquorQuestionRepository liquorQuestionRepository;
    private final LiquorAnswerRepository liquorAnswerRepository;

    /**
     * 단일 질문 - 답변 세트 조회
     */
    public LiquorQuestionTotalRes getQuestionSet(
            Long questionPriKey
    ) {
        try {
            if (questionPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "Question PriKey NULL");
            Optional<LiquorQuestionDto> liquorQuestionDto =
                    liquorQuestionRepository.findByPriKey(questionPriKey);
            if (liquorQuestionDto.isEmpty())
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND Question DATA");
            List<LiquorAnswerDto> liquorAnswerDtos =
                    liquorAnswerRepository.findByQuestionPriKey(liquorQuestionDto.get().getId());
            return LiquorQuestionTotalRes.of(
                    liquorQuestionDto.get(),
                    liquorAnswerDtos
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 질문 - 답변 세트 조회
     */
    public List<LiquorQuestionTotalRes> getAllQuestionAnswer() {
        try {
            List<LiquorQuestionTotalRes> resultList = new ArrayList<>();
            List<LiquorQuestionDto> liquorQuestionDtos =
                    liquorQuestionRepository.findAllQuestion();
            for (LiquorQuestionDto dto: liquorQuestionDtos) {
                List<LiquorAnswerDto> liquorAnswerDtos =
                        liquorAnswerRepository.findByQuestionPriKey(dto.getId());
                if (!liquorAnswerDtos.isEmpty()) {
                    resultList.add(
                            LiquorQuestionTotalRes.of(
                                    dto,
                                    liquorAnswerDtos
                            )
                    );
                }
            }
            return resultList;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 질문 조회
     */
    public List<LiquorQuestionDto> getAllLiquorQuestion () {
        try {
            return liquorQuestionRepository.findAllQuestion();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
