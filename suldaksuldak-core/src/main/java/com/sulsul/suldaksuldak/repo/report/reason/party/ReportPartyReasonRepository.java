package com.sulsul.suldaksuldak.repo.report.reason.party;

import com.sulsul.suldaksuldak.domain.report.QReportPartyReason;
import com.sulsul.suldaksuldak.domain.report.ReportPartyReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ReportPartyReasonRepository extends
        JpaRepository<ReportPartyReason, Long>,
        ReportPartyReasonRepositoryCustom,
        QuerydslPredicateExecutor<ReportPartyReason>,
        QuerydslBinderCustomizer<QReportPartyReason>
{
    @Override
    default void customize(QuerydslBindings bindings, QReportPartyReason root) {
        bindings.excludeUnlistedProperties(true);
    }
}
