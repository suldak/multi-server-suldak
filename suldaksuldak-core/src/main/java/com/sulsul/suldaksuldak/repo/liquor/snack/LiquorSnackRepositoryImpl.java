package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sulsul.suldaksuldak.domain.liquor.QLiquorSnack.liquorSnack;

@Repository
@RequiredArgsConstructor
public class LiquorSnackRepositoryImpl implements LiquorSnackRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private JPAQuery<LiquorSnackDto> getLiquorSnackDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorSnackDto.class,
                                liquorSnack.id,
                                liquorSnack.name
                        )
                );
    }
}
