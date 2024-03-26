package com.sulsul.suldaksuldak.repo.search.text;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.dto.search.SearchTextRankingDto;
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
            Long userPriKey,
            Integer limitNum
    ) {
        return getSearchTextDtoQuery()
                .from(searchText)
                .innerJoin(searchText.user, user)
                .on(searchText.user.id.eq(userPriKey))
                .where(
                        isTagEq(false),
                        searchAtBetween(searchStartTime, searchEndTime)
                )
                .orderBy(searchText.searchAt.desc())
                .fetch();
    }

    @Override
    public List<SearchTextRankingDto> findRankingList(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer limitNum
    ) {
        return getSearchTextRankingDtoQuery()
                .from(searchText)
                .innerJoin(searchText.user, user)
                .on(searchText.user.id.eq(user.id))
                .where(searchAtBetween(searchStartTime, searchEndTime))
                .limit(limitNum)
                .groupBy(searchText.content)
                .orderBy(
                        searchText.content.count().desc()
                )
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

    private JPAQuery<SearchTextRankingDto> getSearchTextRankingDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                SearchTextRankingDto.class,
                                searchText.content
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

    private BooleanExpression isTagEq(
            Boolean isTag
    ) {
        return isTag == null ? null :
                searchText.isTag.eq(isTag);
    }
}
