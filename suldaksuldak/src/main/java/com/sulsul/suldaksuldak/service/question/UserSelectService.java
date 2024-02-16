package com.sulsul.suldaksuldak.service.question;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import com.sulsul.suldaksuldak.domain.question.UserSelect;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.question.AnswerWeightDto;
import com.sulsul.suldaksuldak.dto.question.UserSelectDto;
import com.sulsul.suldaksuldak.dto.question.UserSelectReq;
import com.sulsul.suldaksuldak.dto.question.UserSelectReq.QuestionAnswerObj;
import com.sulsul.suldaksuldak.dto.question.UserSelectRes;
import com.sulsul.suldaksuldak.dto.stats.user.UserTagDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.user.UserRepository;
import com.sulsul.suldaksuldak.repo.question.answer.LiquorAnswerRepository;
import com.sulsul.suldaksuldak.repo.question.question.LiquorQuestionRepository;
import com.sulsul.suldaksuldak.repo.question.user.UserSelectRepository;
import com.sulsul.suldaksuldak.repo.question.weight.AnswerWeightRepository;
import com.sulsul.suldaksuldak.repo.stats.user.UserTagRepository;
import com.sulsul.suldaksuldak.service.stats.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSelectService {
    private final UserSelectRepository userSelectRepository;
    private final UserRepository userRepository;
    private final LiquorQuestionRepository liquorQuestionRepository;
    private final LiquorAnswerRepository liquorAnswerRepository;
    private final AnswerWeightRepository answerWeightRepository;
    private final UserTagRepository userTagRepository;
    private final StatsService statsService;

    public Boolean createUserSelectData (
            Long userPriKey,
            UserSelectReq userSelectReq
    ) {
        try {
            // 유저 기본키가 없으면 오류 발생
            if (userPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "USER PRI KEY NULL");

            // 유저를 기본키로 찾고, 찾지 못하면 오류 발생
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND USER DATA");

            // 해당 유저에 이미 있던 답변 가중치 조회 및 감소
            List<UserSelectDto> userSelectDtos = userSelectRepository
                    .findByUserPriKey(userPriKey);
            for (UserSelectDto userSelectDto: userSelectDtos) {
                List<AnswerWeightDto> answerWeightDtos =
                        answerWeightRepository.findByLiquorAnswerPriKey(
                                userSelectDto.getLiquorAnswerId()
                        );
                for (AnswerWeightDto weightDto: answerWeightDtos) {
                    Optional<UserTagDto> userTagDto =
                            userTagRepository.findByUserPriKeyAndTagTypeAndTagId(
                                    user.get().getId(),
                                    weightDto.getTagType(),
                                    weightDto.getTagId()
                            );
                    userTagDto
                            .flatMap(tagDto -> userTagRepository.findById(tagDto.getId()))
                            .ifPresent(findEntity -> {
                                userTagRepository.save(
                                        UserTagDto.updateWeight(
                                                findEntity,
                                                -weightDto.getWeight()
                                        )
                                );
                            });
                }
                // 이미 있던 질문 답변 데이터 삭제
                userSelectRepository.deleteById(userSelectDto.getId());
            }

            // 질문 / 답변 세트를 반복
            for (QuestionAnswerObj questionAnswerObj: userSelectReq.getQuestionAnswerMap()) {
                // 질문 기본키로 조회하고 없으면 continue
                Optional<LiquorQuestion> liquorQuestion =
                        liquorQuestionRepository.findById(questionAnswerObj.getQuestionPriKey());
                if (liquorQuestion.isEmpty()) continue;

                // 질문의 답변 기본키 리스트로 반복
                for (Long answerPriKey: questionAnswerObj.getAnswerPriKeyList()) {
                    // 질문 기본키로 조회하고, 없으면 continue
                    Optional<LiquorAnswer> liquorAnswer =
                            liquorAnswerRepository.findById(answerPriKey);
                    if (liquorAnswer.isEmpty()) continue;
                    // Req의 질문 기본키과 조회한 질문의 기본기가 같으면 Req로 받은 정보 저장
                    if (questionAnswerObj.getQuestionPriKey()
                            .equals(liquorAnswer.get().getLiquorQuestion().getId()))
                    {
                        String priKey =
                                user.get().getId() + "_" +
                                        liquorQuestion.get().getId() + "_" +
                                        liquorAnswer.get().getId();
                        userSelectRepository.save(
                                UserSelect.of(
                                        priKey,
                                        liquorQuestion.get(),
                                        liquorAnswer.get(),
                                        user.get()
                                )
                        );
                        // 답변의 가중치에 맞게 유저의 가중치 증가
                        List<AnswerWeightDto> answerWeightDtos =
                                answerWeightRepository.findByLiquorAnswerPriKey(
                                        answerPriKey
                                );
                        for (AnswerWeightDto weightDto: answerWeightDtos) {
                            statsService.countTagCnt(
                                    user.get(),
                                    weightDto.getTagType(),
                                    weightDto.getTagId(),
                                    weightDto.getWeight()
                            );
                        }
                    }
                }
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    public UserSelectRes getUserSelectRes (
            Long userPriKey
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "User PriKey NULL");
            List<UserSelectDto> userSelectDtos =
                    userSelectRepository.findByUserPriKey(userPriKey);
            return UserSelectRes.of(userSelectDtos);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
