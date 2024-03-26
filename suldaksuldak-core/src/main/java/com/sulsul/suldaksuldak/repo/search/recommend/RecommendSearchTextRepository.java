package com.sulsul.suldaksuldak.repo.search.recommend;

import com.sulsul.suldaksuldak.domain.search.QRecommendSearchText;
import com.sulsul.suldaksuldak.domain.search.RecommendSearchText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RecommendSearchTextRepository extends
        JpaRepository<RecommendSearchText, Long>,
        RecommendSearchTextRepositoryCustom,
        QuerydslPredicateExecutor<RecommendSearchText>,
        QuerydslBinderCustomizer<QRecommendSearchText>
{
    @Override
    default void customize(QuerydslBindings bindings, QRecommendSearchText root) {
        bindings.excludeUnlistedProperties(true);
    }
}
