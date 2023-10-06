package com.sulsul.suldaksuldak.repo.stats.search;

import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.domain.stats.QLiquorSearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorSearchLogRepository extends
        JpaRepository<LiquorSearchLog, String>,
        LiquorSearchLogRepositoryCustom,
        QuerydslPredicateExecutor<LiquorSearchLog>,
        QuerydslBinderCustomizer<QLiquorSearchLog>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorSearchLog root) {
        bindings.excludeUnlistedProperties(true);
    }
}
