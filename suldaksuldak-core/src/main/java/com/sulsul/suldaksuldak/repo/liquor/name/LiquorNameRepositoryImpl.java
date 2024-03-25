package com.sulsul.suldaksuldak.repo.liquor.name;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.LiquorNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sulsul.suldaksuldak.domain.tag.QLiquorName.liquorName;

@Repository
@RequiredArgsConstructor
public class LiquorNameRepositoryImpl implements LiquorNameRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LiquorNameDto> findByPriKey(Long priKey) {
        return Optional.ofNullable(
                getLiquorNameDtoQuery()
                        .from(liquorName)
                        .leftJoin(liquorName.fileBase)
                        .where(liquorName.id.eq(priKey))
                        .fetchFirst()
        );
    }

    @Override
    public List<LiquorNameDto> findByPriKeyList(List<Long> priKeyList) {
        return getLiquorNameDtoQuery()
                .from(liquorName)
                .where(priKeyListIn(priKeyList))
                .fetch();
    }

    private JPAQuery<LiquorNameDto> getLiquorNameDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LiquorNameDto.class,
                                liquorName.id,
                                liquorName.name,
                                liquorName.fileBase.fileNm
                        )
                );
    }

    private BooleanExpression priKeyListIn(
            List<Long> priKeyList
    ) {
        if (priKeyList == null || priKeyList.isEmpty())
            return null;
        return liquorName.id.in(priKeyList);
    }
}
