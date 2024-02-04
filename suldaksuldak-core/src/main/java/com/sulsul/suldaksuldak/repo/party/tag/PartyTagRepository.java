package com.sulsul.suldaksuldak.repo.party.tag;

import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.party.QPartyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyTagRepository extends
        JpaRepository<PartyTag, Long>,
        PartyTagRepositoryCustom,
        QuerydslPredicateExecutor<PartyTag>,
        QuerydslBinderCustomizer<QPartyTag>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyTag root) {
        bindings.excludeUnlistedProperties(true);
    }
}
