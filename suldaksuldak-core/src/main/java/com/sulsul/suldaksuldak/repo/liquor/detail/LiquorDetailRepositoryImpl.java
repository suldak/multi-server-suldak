package com.sulsul.suldaksuldak.repo.liquor.detail;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
