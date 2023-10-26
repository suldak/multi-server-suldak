package com.sulsul.suldaksuldak.repo.question.answer;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.question.LiquorAnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.question.QLiquorAnswer.liquorAnswer;
import static com.sulsul.suldaksuldak.domain.question.QLiquorQuestion.liquorQuestion;

@Repository
@RequiredArgsConstructor
public class LiquorAnswerRepositoryImpl
        implements LiquorAnswerRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LiquorAnswerDto> findByQuestionPriKey(Long questionPriKey) {
        return getLiquorAnswerDtoQuery()
                .from(liquorAnswer)
                .innerJoin(liquorAnswer.liquorQuestion, liquorQuestion)
                .on(liquorAnswer.liquorQuestion.id.eq(questionPriKey))
                .orderBy(liquorAnswer.aIndex.asc())
                .fetch();
    }

    private JPAQuery<LiquorAnswerDto> getLiquorAnswerDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorAnswerDto.class,
                                liquorAnswer.id,
                                liquorAnswer.aIndex,
                                liquorAnswer.aText,
                                liquorAnswer.liquorQuestion.id
                        )
                );
    }
}
