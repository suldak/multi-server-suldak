package com.sulsul.suldaksuldak.repo.party;

import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.QParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyRepository extends
        JpaRepository<Party, Long>,
        PartyRepositoryCustom,
        QuerydslPredicateExecutor<Party>,
        QuerydslBinderCustomizer<QParty>
{
    @Override
    default void customize(QuerydslBindings bindings, QParty root) {
        bindings.excludeUnlistedProperties(true);
    }
}
