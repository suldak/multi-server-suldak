package com.sulsul.suldaksuldak.service.question;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import com.sulsul.suldaksuldak.domain.question.UserSelect;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.question.UserSelectDto;
import com.sulsul.suldaksuldak.dto.question.UserSelectReq;
import com.sulsul.suldaksuldak.dto.question.UserSelectReq.QuestionAnswerObj;
import com.sulsul.suldaksuldak.dto.question.UserSelectRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.question.answer.LiquorAnswerRepository;
import com.sulsul.suldaksuldak.repo.question.question.LiquorQuestionRepository;
import com.sulsul.suldaksuldak.repo.question.user.UserSelectRepository;
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

    public Boolean createUserSelectData (
            UserSelectReq userSelectReq
    ) {
        try {
            if (userSelectReq.getUserPriKey() == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "USER PRI KEY NULL");

            Optional<User> user = userRepository.findById(userSelectReq.getUserPriKey());
            if (user.isEmpty())
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND USER DATA");

            for (QuestionAnswerObj questionAnswerObj: userSelectReq.getQuestionAnswerMap()) {
                Optional<LiquorQuestion> liquorQuestion =
                        liquorQuestionRepository.findById(questionAnswerObj.getQuestionPriKey());
                if (liquorQuestion.isEmpty()) continue;

                List<UserSelectDto> userSelectDtos = userSelectRepository
                        .findByUserPriKeyAndQuestionPriKey(
                                userSelectReq.getUserPriKey(),
                                questionAnswerObj.getQuestionPriKey()
                        );
                for (UserSelectDto dto: userSelectDtos) {
                    userSelectRepository.deleteById(dto.getId());
                }

                for (Long answerPriKey: questionAnswerObj.getAnswerPriKeyList()) {
                    Optional<LiquorAnswer> liquorAnswer =
                            liquorAnswerRepository.findById(answerPriKey);
                    if (liquorAnswer.isEmpty()) continue;
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
