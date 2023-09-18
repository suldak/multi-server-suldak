package com.sulsul.suldaksuldak.repo.tag.abv;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorAbvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.tag.QLiquorAbv.liquorAbv;

@Repository
@RequiredArgsConstructor
public class LiquorAbvRepositoryImpl implements LiquorAbvRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorAbvDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorAbvDtoQuery()
                        .from(liquorAbv)
                        .where(liquorAbv.id.eq(priKey))
                        .fetchFirst()
        );
    }

    private JPAQuery<LiquorAbvDto> getLiquorAbvDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorAbvDto.class,
                                liquorAbv.id,
                                liquorAbv.name
                        )
                );
    }
}
