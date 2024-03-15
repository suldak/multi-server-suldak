package com.sulsul.suldaksuldak.repo.party.complete;

import com.sulsul.suldaksuldak.domain.party.PartyComplete;
import com.sulsul.suldaksuldak.domain.party.QPartyComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyCompleteRepository extends
        JpaRepository<PartyComplete, Long>,
        PartyCompleteRepositoryCustom,
        QuerydslPredicateExecutor<PartyComplete>,
        QuerydslBinderCustomizer<QPartyComplete>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyComplete root) {
        bindings.excludeUnlistedProperties(true);
    }
}
