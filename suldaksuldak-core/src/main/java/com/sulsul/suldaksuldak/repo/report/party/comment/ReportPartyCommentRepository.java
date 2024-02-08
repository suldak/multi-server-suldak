package com.sulsul.suldaksuldak.repo.report.party.comment;

import com.sulsul.suldaksuldak.domain.report.QReportPartyComment;
import com.sulsul.suldaksuldak.domain.report.ReportPartyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ReportPartyCommentRepository extends
        JpaRepository<ReportPartyComment, Long>,
        ReportPartyCommentRepositoryCustom,
        QuerydslPredicateExecutor<ReportPartyComment>,
        QuerydslBinderCustomizer<QReportPartyComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QReportPartyComment root) {
        bindings.excludeUnlistedProperties(true);
    }
}
