package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.bridge.QSnToLi.snToLi;
import static com.sulsul.suldaksuldak.domain.file.QFileBase.fileBase;
import static com.sulsul.suldaksuldak.domain.tag.QLiquorSnack.liquorSnack;

@Repository
@RequiredArgsConstructor
public class LiquorSnackRepositoryImpl implements LiquorSnackRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LiquorSnackDto> findAllLiquorSnack() {
        return getLiquorSnackDtoQuery()
                .from(liquorSnack)
                .leftJoin(liquorSnack.fileBase, fileBase)
                .orderBy(liquorSnack.name.asc())
                .fetch();
    }

    @Override
    public List<LiquorSnackDto> findByLiquorPriKey(Long liquorPriKey) {
        return getLiquorSnackDtoQuery()
                .from(liquorSnack)
                .innerJoin(liquorSnack.snToLis, snToLi)
                .on(snToLi.liquor.id.eq(liquorPriKey))
                .leftJoin(liquorSnack.fileBase, fileBase)
                .fetch();
    }

    private JPAQuery<LiquorSnackDto> getLiquorSnackDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorSnackDto.class,
                                liquorSnack.id,
                                liquorSnack.name,
                                liquorSnack.fileBase.fileNm
                        )
                );
    }

    private BooleanExpression priKeyIn(
            List<Long> priKeyList
    ) {
        return priKeyList == null || priKeyList.isEmpty() ? null : liquorSnack.id.in(priKeyList);
    }
}
