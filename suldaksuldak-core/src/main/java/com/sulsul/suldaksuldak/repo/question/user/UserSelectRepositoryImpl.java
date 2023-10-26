package com.sulsul.suldaksuldak.repo.question.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.question.UserSelectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.question.QLiquorAnswer.liquorAnswer;
import static com.sulsul.suldaksuldak.domain.question.QLiquorQuestion.liquorQuestion;
import static com.sulsul.suldaksuldak.domain.question.QUserSelect.userSelect;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserSelectRepositoryImpl
        implements UserSelectRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserSelectDto> findByUserPriKey(Long userPriKey) {
        return getUserSelectDtoQuery()
                .from(userSelect)
                .innerJoin(userSelect.user, user)
                .on(userSelect.user.id.eq(userPriKey))
                .innerJoin(userSelect.liquorQuestion, liquorQuestion)
                .on(userSelect.liquorQuestion.id.eq(liquorQuestion.id))
                .innerJoin(userSelect.liquorAnswer, liquorAnswer)
                .on(userSelect.liquorAnswer.id.eq(liquorAnswer.id))
                .fetch();
    }

    @Override
    public List<UserSelectDto> findByUserPriKeyAndQuestionPriKey(
            Long userPriKey,
            Long questionPriKey
    ) {
        return getUserSelectDtoQuery()
                .from(userSelect)
                .innerJoin(userSelect.user, user)
                .on(userSelect.user.id.eq(userPriKey))
                .innerJoin(userSelect.liquorQuestion, liquorQuestion)
                .on(userSelect.liquorQuestion.id.eq(questionPriKey))
                .innerJoin(userSelect.liquorAnswer, liquorAnswer)
                .on(userSelect.liquorAnswer.id.eq(liquorAnswer.id))
                .fetch();
    }

    private JPAQuery<UserSelectDto> getUserSelectDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserSelectDto.class,
                                userSelect.id,
                                userSelect.liquorQuestion.id,
                                userSelect.liquorAnswer.id,
                                userSelect.user.id
                        )
                );
    }
}
