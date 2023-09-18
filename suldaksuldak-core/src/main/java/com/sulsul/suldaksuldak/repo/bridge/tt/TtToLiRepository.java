package com.sulsul.suldaksuldak.repo.bridge.tt;

import com.sulsul.suldaksuldak.domain.bridge.QTtToLi;
import com.sulsul.suldaksuldak.domain.bridge.TtToLi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface TtToLiRepository extends
        JpaRepository<TtToLi, Long>,
        TtToLiRepositoryCustom,
        QuerydslPredicateExecutor<TtToLi>,
        QuerydslBinderCustomizer<QTtToLi>
{
    @Override
    default void customize(QuerydslBindings bindings, QTtToLi root) {
        bindings.excludeUnlistedProperties(true);
    }
}
