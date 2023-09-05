package com.sulsul.suldaksuldak.repo.tag.state;

import com.sulsul.suldaksuldak.domain.tag.QStateType;
import com.sulsul.suldaksuldak.domain.tag.StateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface StateTypeRepository extends
        JpaRepository<StateType, Long>,
        StateTypeRepositoryCustom,
        QuerydslPredicateExecutor<StateType>,
        QuerydslBinderCustomizer<QStateType>
{
    @Override
    default void customize(QuerydslBindings bindings, QStateType root) {
        bindings.excludeUnlistedProperties(true);
    }
}
