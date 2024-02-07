package com.sulsul.suldaksuldak.repo.report.party;

import com.sulsul.suldaksuldak.domain.report.QReportParty;
import com.sulsul.suldaksuldak.domain.report.ReportParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ReportPartyRepository extends
        JpaRepository<ReportParty, Long>,
        ReportPartyRepositoryCustom,
        QuerydslPredicateExecutor<ReportParty>,
        QuerydslBinderCustomizer<QReportParty>
{
    @Override
    default void customize(QuerydslBindings bindings, QReportParty root) {
        bindings.excludeUnlistedProperties(true);
    }
}
