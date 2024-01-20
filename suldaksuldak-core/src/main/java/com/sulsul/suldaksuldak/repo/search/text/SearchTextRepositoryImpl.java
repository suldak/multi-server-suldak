package com.sulsul.suldaksuldak.repo.search.text;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.sulsul.suldaksuldak.domain.search.QSearchText.searchText;
import static com.sulsul.suldaksuldak.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class SearchTextRepositoryImpl
    implements SearchTextRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SearchTextDto> findListByOption(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Long userPriKey
    ) {
        return getSearchTextDtoQuery()
                .from(searchText)
                .innerJoin(searchText.user, user)
                .on(
                        userPriKey == null ?
                                searchText.user.id.eq(user.id) :
                                searchText.user.id.eq(userPriKey)
                )
                .where(searchAtBetween(searchStartTime, searchEndTime))
                .fetch();
    }

    private JPAQuery<SearchTextDto> getSearchTextDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                SearchTextDto.class,
                                searchText.user.id,
                                searchText.user.nickname,
                                searchText.content,
                                searchText.searchAt
                        )
                );
    }

    private BooleanExpression searchAtBetween(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if ((startAt == null) || (endAt == null)) return null;
        return searchText.searchAt.between(startAt, endAt);
    }
}
