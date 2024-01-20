package com.sulsul.suldaksuldak.repo.search.text;

import com.sulsul.suldaksuldak.domain.search.QSearchText;
import com.sulsul.suldaksuldak.domain.search.SearchText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface SearchTextRepository extends
        JpaRepository<SearchText, String>,
        SearchTextRepositoryCustom,
        QuerydslPredicateExecutor<SearchText>,
        QuerydslBinderCustomizer<QSearchText>
{
    @Override
    default void customize(QuerydslBindings bindings, QSearchText root) {
        bindings.excludeUnlistedProperties(true);
    }
}
