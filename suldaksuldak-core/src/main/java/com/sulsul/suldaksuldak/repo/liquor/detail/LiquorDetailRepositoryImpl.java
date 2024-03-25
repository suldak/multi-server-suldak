package com.sulsul.suldaksuldak.repo.liquor.detail;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.tag.QLiquorDetail.liquorDetail;

@Repository
@RequiredArgsConstructor
public class LiquorDetailRepositoryImpl implements LiquorDetailRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorDetailDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorDetailDtoQuery()
                        .from(liquorDetail)
                        .where(liquorDetail.id.eq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<LiquorDetailDto> findByPriKeyList(List<Long> priKeyList) {
        return getLiquorDetailDtoQuery()
                .from(liquorDetail)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<LiquorDetailDto> getLiquorDetailDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorDetailDto.class,
                                liquorDetail.id,
                                liquorDetail.name
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        if (priKeyList == null || priKeyList.isEmpty())
            return null;
        return liquorDetail.id.in(priKeyList);
    }
}
