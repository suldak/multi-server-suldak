package com.sulsul.suldaksuldak.repo.admin.feedback;

import com.sulsul.suldaksuldak.domain.admin.feedback.QUserPartyFeedback;
import com.sulsul.suldaksuldak.domain.admin.feedback.UserPartyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserPartyFeedbackRepository extends
        JpaRepository<UserPartyFeedback, Long>,
        UserPartyFeedbackRepositoryCustom,
        QuerydslPredicateExecutor<UserPartyFeedback>,
        QuerydslBinderCustomizer<QUserPartyFeedback>
{
    @Override
    default void customize(QuerydslBindings bindings, QUserPartyFeedback root) {
        bindings.excludeUnlistedProperties(true);
    }
}
