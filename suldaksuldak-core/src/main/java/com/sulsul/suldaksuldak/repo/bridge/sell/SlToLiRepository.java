package com.sulsul.suldaksuldak.repo.bridge.sell;

import com.sulsul.suldaksuldak.domain.bridge.QSlToLi;
import com.sulsul.suldaksuldak.domain.bridge.SlToLi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface SlToLiRepository extends
        JpaRepository<SlToLi, Long>,
        SlToLiRepositoryCustom,
        QuerydslPredicateExecutor<SlToLi>,
        QuerydslBinderCustomizer<QSlToLi>
{
    @Override
    default void customize(QuerydslBindings bindings, QSlToLi root) {
        bindings.excludeUnlistedProperties(true);
    }
}
