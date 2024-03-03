package com.sulsul.suldaksuldak.repo.stats.party;

import com.sulsul.suldaksuldak.domain.stats.PartySearchLog;
import com.sulsul.suldaksuldak.domain.stats.QPartySearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartySearchLogRepository extends
        JpaRepository<PartySearchLog, String>,
        PartySearchLogRepositoryCustom,
        QuerydslPredicateExecutor<PartySearchLog>,
        QuerydslBinderCustomizer<QPartySearchLog>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartySearchLog root) {
        bindings.excludeUnlistedProperties(true);
    }
}
