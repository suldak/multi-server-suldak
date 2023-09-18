package com.sulsul.suldaksuldak.repo.bridge.st;

import com.sulsul.suldaksuldak.domain.bridge.QStToLi;
import com.sulsul.suldaksuldak.domain.bridge.StToLi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface StToLiRepository extends
        JpaRepository<StToLi, Long>,
        StToLiRepositoryCustom,
        QuerydslPredicateExecutor<StToLi>,
        QuerydslBinderCustomizer<QStToLi>
{
    @Override
    default void customize(QuerydslBindings bindings, QStToLi root) {
        bindings.excludeUnlistedProperties(true);
    }
}
