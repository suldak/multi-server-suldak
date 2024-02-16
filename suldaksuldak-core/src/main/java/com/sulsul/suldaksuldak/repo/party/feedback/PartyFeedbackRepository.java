package com.sulsul.suldaksuldak.repo.party.feedback;

import com.sulsul.suldaksuldak.domain.party.PartyFeedback;
import com.sulsul.suldaksuldak.domain.party.QPartyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyFeedbackRepository extends
        JpaRepository<PartyFeedback, Long>,
        PartyFeedbackRepositoryCustom,
        QuerydslPredicateExecutor<PartyFeedback>,
        QuerydslBinderCustomizer<QPartyFeedback>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyFeedback root) {
        bindings.excludeUnlistedProperties(true);
    }
}
