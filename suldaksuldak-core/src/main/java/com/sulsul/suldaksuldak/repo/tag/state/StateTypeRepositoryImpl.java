package com.sulsul.suldaksuldak.repo.tag.state;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.tag.StateTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sulsul.suldaksuldak.domain.bridge.QStToLi.stToLi;
import static com.sulsul.suldaksuldak.domain.tag.QStateType.stateType;

@Repository
@RequiredArgsConstructor
public class StateTypeRepositoryImpl implements StateTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StateTypeDto> findByLiquorPriKey(Long liquorPriKey) {
        return getStateTypeDtoQuery()
                .from(stateType)
                .innerJoin(stateType.stToLis, stToLi)
                .on(stToLi.liquor.id.eq(liquorPriKey))
                .fetch();
    }

    private JPAQuery<StateTypeDto> getStateTypeDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                StateTypeDto.class,
                                stateType.id,
                                stateType.name
                        )
                );
    }
}
