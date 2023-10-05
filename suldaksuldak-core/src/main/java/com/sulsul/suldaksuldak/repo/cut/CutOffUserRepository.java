package com.sulsul.suldaksuldak.repo.cut;

import com.sulsul.suldaksuldak.domain.user.CutOffUser;
import com.sulsul.suldaksuldak.domain.user.QCutOffUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CutOffUserRepository extends
        JpaRepository<CutOffUser, Long>,
        CutOffUserRepositoryCustom,
        QuerydslPredicateExecutor<CutOffUser>,
        QuerydslBinderCustomizer<QCutOffUser>
{
    @Override
    default void customize(QuerydslBindings bindings, QCutOffUser root) {
        bindings.excludeUnlistedProperties(true);
    }
}
