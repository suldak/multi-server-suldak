package com.sulsul.suldaksuldak.repo.bridge.mt;

import com.sulsul.suldaksuldak.domain.bridge.MtToLi;
import com.sulsul.suldaksuldak.domain.bridge.QMtToLi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MtToLiRepository extends
        JpaRepository<MtToLi, Long>,
        MtToLiRepositoryCumstom,
        QuerydslPredicateExecutor<MtToLi>,
        QuerydslBinderCustomizer<QMtToLi>
{
    @Override
    default void customize(QuerydslBindings bindings, QMtToLi root) {
        bindings.excludeUnlistedProperties(true);
    }
}
