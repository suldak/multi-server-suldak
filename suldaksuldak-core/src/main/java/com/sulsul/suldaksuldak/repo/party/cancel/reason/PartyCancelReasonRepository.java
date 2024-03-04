package com.sulsul.suldaksuldak.repo.party.cancel.reason;

import com.sulsul.suldaksuldak.domain.party.cancel.PartyCancelReason;
import com.sulsul.suldaksuldak.domain.party.cancel.QPartyCancelReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyCancelReasonRepository extends
        JpaRepository<PartyCancelReason, Long>,
        PartyCancelReasonRepositoryCustom,
        QuerydslPredicateExecutor<PartyCancelReason>,
        QuerydslBinderCustomizer<QPartyCancelReason>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyCancelReason root) {
        bindings.excludeUnlistedProperties(true);
    }
}
