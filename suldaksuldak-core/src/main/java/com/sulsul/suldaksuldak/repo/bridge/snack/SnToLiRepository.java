package com.sulsul.suldaksuldak.repo.bridge.snack;

import com.sulsul.suldaksuldak.domain.bridge.QSnToLi;
import com.sulsul.suldaksuldak.domain.bridge.SnToLi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface SnToLiRepository extends
        JpaRepository<SnToLi, Long>,
        SnToLiRepositoryCustom,
        QuerydslPredicateExecutor<SnToLi>,
        QuerydslBinderCustomizer<QSnToLi>
{
    @Override
    default void customize(QuerydslBindings bindings, QSnToLi root) {
        bindings.excludeUnlistedProperties(true);
    }
}
