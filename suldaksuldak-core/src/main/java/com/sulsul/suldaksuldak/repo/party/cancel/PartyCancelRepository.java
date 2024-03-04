package com.sulsul.suldaksuldak.repo.party.cancel;

import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancel;
import com.sulsul.suldaksuldak.domain.party.cancel.QPartyCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyCancelRepository extends
        JpaRepository<PartyCancel, Long>,
        PartyCancelRepositoryCustom,
        QuerydslPredicateExecutor<PartyCancel>,
        QuerydslBinderCustomizer<QPartyCancel>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyCancel root) {
        bindings.excludeUnlistedProperties(true);
    }
}
