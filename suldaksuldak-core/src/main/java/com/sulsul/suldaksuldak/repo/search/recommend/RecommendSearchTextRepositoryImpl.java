package com.sulsul.suldaksuldak.repo.search.recommend;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.search.RecommendSearchTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.search.QRecommendSearchText.recommendSearchText;

@Repository
@RequiredArgsConstructor
public class RecommendSearchTextRepositoryImpl
    implements RecommendSearchTextRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RecommendSearchTextDto> findByOption(
            Boolean isActive,
            Integer limit
    ) {
        return getRecommendSearchTextDtoQuery()
                .from(recommendSearchText)
                .where(
                        isActiveEq(isActive)
                )
                .limit(limit)
                .fetch();
    }

    private JPAQuery<RecommendSearchTextDto> getRecommendSearchTextDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                RecommendSearchTextDto.class,
                                recommendSearchText.id,
                                recommendSearchText.isActive,
                                recommendSearchText.text
                        )
                );
    }

    private BooleanExpression isActiveEq(
            Boolean isActive
    ) {
        return isActive == null ? null :
                recommendSearchText.isActive.eq(isActive);
    }
}
