package com.sulsul.suldaksuldak.repo.auth.party;

import com.sulsul.suldaksuldak.domain.user.QUserPartyFeedback;
import com.sulsul.suldaksuldak.domain.user.UserPartyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserPartyFeedbackRepository extends
        JpaRepository<UserPartyFeedback, Long>,
        UserPartyFeedbackRepositoryCumstom,
        QuerydslPredicateExecutor<UserPartyFeedback>,
        QuerydslBinderCustomizer<QUserPartyFeedback>
{
    @Override
    default void customize(QuerydslBindings bindings, QUserPartyFeedback root) {
        bindings.excludeUnlistedProperties(true);
    }
}
