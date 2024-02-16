package com.sulsul.suldaksuldak.repo.user;

import com.sulsul.suldaksuldak.domain.user.QUser;
import com.sulsul.suldaksuldak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserRepository extends
        JpaRepository<User, Long>,
        UserRepositoryCustom,
        QuerydslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser>
{
    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        bindings.excludeUnlistedProperties(true);
    }
}
