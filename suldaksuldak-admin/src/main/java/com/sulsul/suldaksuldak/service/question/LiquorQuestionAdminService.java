package com.sulsul.suldaksuldak.service.question;


import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import com.sulsul.suldaksuldak.dto.question.LiquorAnswerDto;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.question.answer.LiquorAnswerRepository;
import com.sulsul.suldaksuldak.repo.question.question.LiquorQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorQuestionAdminService {
    private final LiquorQuestionRepository liquorQuestionRepository;
    private final LiquorAnswerRepository liquorAnswerRepository;

    /**
     * 질문 등록 및 수정
     */
    public Boolean createLiquorQuestion (
            LiquorQuestionDto liquorQuestionDto
    ) {
        try {
            if (liquorQuestionDto.getId() == null) {
                liquorQuestionRepository.save(
                        liquorQuestionDto.toEntity()
                );
            } else {
                liquorQuestionRepository.findById(liquorQuestionDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorQuestionRepository.save(
                                            liquorQuestionDto.updateEntity(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "NOT FOUND Question"
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

    /**
     * 질문 삭제
     */
    public Boolean deleteLiquorQuestion (Long priKey) {
        try {
            if (priKey == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "ID NULL");
            liquorQuestionRepository.deleteById(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 답변 등록 및 수정
     */
    public Boolean createLiquorAnswer(
            LiquorAnswerDto liquorAnswerDto
    ) {
        try {
            if (liquorAnswerDto.getLiquorQuestionId() == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "LiquorQuestionId NULL");
            if (liquorAnswerDto.getId() == null) {
                Optional<LiquorQuestion> liquorQuestion =
                        liquorQuestionRepository.findById(liquorAnswerDto.getLiquorQuestionId());
                if (liquorQuestion.isEmpty())
                    throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND LiquorQuestion DATA");

                liquorAnswerRepository.save(
                        liquorAnswerDto.toEntity(liquorQuestion.get())
                );
            } else {
                liquorAnswerRepository.findById(liquorAnswerDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorAnswerRepository.save(
                                            liquorAnswerDto.updateEntity(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "LiquorAnswerData NOT FOUND"
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

    /**
     * 답변 삭제
     */
    public Boolean deleteLiquorAnswer (Long priKey) {
        try {
            if (priKey == null) throw new GeneralException(ErrorCode.NOT_FOUND, "LiquorAnswerPriKey NULL");
            liquorAnswerRepository.deleteById(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
