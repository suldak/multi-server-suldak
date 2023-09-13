package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.liquor.QLiquor.liquor;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LiquorRepositoryImpl implements LiquorRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorDtoQuery()
                        .from(liquor)
                        .where(priKeyEq(priKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<LiquorDto> getLiquorDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorDto.class,
                                liquor.id,
                                liquor.name,
                                liquor.summaryExplanation,
                                liquor.detailExplanation,
                                liquor.createdAt,
                                liquor.modifiedAt
                        )
                );
    }

    private BooleanExpression priKeyEq(Long priKey) {
        return liquor.id.eq(priKey);
    }
}
