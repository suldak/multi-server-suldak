package com.sulsul.suldaksuldak.repo.stats.user.liquor;

import com.sulsul.suldaksuldak.domain.stats.QUserLiquor;
import com.sulsul.suldaksuldak.domain.stats.UserLiquor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserLiquorRepository extends
        JpaRepository<UserLiquor, Long>,
        UserLiquorRepositoryCustom,
        QuerydslPredicateExecutor<UserLiquor>,
        QuerydslBinderCustomizer<QUserLiquor>
{
    @Override
    default void customize(QuerydslBindings bindings, QUserLiquor root) {
        bindings.excludeUnlistedProperties(true);
    }
}
