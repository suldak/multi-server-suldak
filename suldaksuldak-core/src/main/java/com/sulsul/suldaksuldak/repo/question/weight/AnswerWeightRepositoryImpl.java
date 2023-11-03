package com.sulsul.suldaksuldak.repo.question.weight;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.question.AnswerWeightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.question.QAnswerWeight.answerWeight;
import static com.sulsul.suldaksuldak.domain.question.QLiquorAnswer.liquorAnswer;

@Repository
@RequiredArgsConstructor
public class AnswerWeightRepositoryImpl
        implements AnswerWeightRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AnswerWeightDto> findByLiquorAnswerPriKey(
            Long liquorAnswerPriKey
    ) {
        return getAnswerWeightDtoQuery()
                .from(answerWeight)
                .innerJoin(answerWeight.liquorAnswer, liquorAnswer)
                .on(answerWeight.liquorAnswer.id.eq(liquorAnswerPriKey))
                .orderBy(answerWeight.tagType.asc())
                .fetch();
    }

    private JPAQuery<AnswerWeightDto> getAnswerWeightDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                AnswerWeightDto.class,
                                answerWeight.id,
                                answerWeight.tagType,
                                answerWeight.tagId,
                                answerWeight.weight,
                                answerWeight.liquorAnswer.id
                        )
                );
    }
}
