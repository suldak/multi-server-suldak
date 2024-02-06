package com.sulsul.suldaksuldak.repo.party.guest;

import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.QPartyGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyGuestRepository extends
        JpaRepository<PartyGuest, String>,
        PartyGuestRepositoryCustom,
        QuerydslPredicateExecutor<PartyGuest>,
        QuerydslBinderCustomizer<QPartyGuest>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyGuest root) {
        bindings.excludeUnlistedProperties(true);
    }
}
