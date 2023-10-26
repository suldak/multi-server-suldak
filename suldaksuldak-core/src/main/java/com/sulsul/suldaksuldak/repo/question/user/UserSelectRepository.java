package com.sulsul.suldaksuldak.repo.question.user;

import com.sulsul.suldaksuldak.domain.question.QUserSelect;
import com.sulsul.suldaksuldak.domain.question.UserSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserSelectRepository extends
        JpaRepository<UserSelect, String>,
        UserSelectRepositoryCustom,
        QuerydslPredicateExecutor<UserSelect>,
        QuerydslBinderCustomizer<QUserSelect>
{
    @Override
    default void customize(QuerydslBindings bindings, QUserSelect root) {
        bindings.excludeUnlistedProperties(true);
    }
}
