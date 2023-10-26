package com.sulsul.suldaksuldak.repo.question.question;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.question.QLiquorQuestion.liquorQuestion;

@Repository
@RequiredArgsConstructor
public class LiquorQuestionRepositoryImpl
        implements LiquorQuestionRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorQuestionDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorQuestionDtoQuery()
                        .from(liquorQuestion)
                        .where(liquorQuestion.id.eq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<LiquorQuestionDto> findByQIndex(Integer qIndex) {
        return Optional.ofNullable(
                getLiquorQuestionDtoQuery()
                        .from(liquorQuestion)
                        .where(liquorQuestion.qIndex.eq(qIndex))
                        .fetchFirst()
        );
    }

    @Override
    public List<LiquorQuestionDto> findAllQuestion() {
        return getLiquorQuestionDtoQuery()
                .from(liquorQuestion)
                .orderBy(liquorQuestion.qIndex.asc())
                .fetch();
    }

    private JPAQuery<LiquorQuestionDto> getLiquorQuestionDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorQuestionDto.class,
                                liquorQuestion.id,
                                liquorQuestion.qIndex,
                                liquorQuestion.qText
                        )
                );
    }
}
