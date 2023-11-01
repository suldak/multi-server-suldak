package com.sulsul.suldaksuldak.repo.admin.auth;

import com.sulsul.suldaksuldak.domain.admin.AdminUser;
import com.sulsul.suldaksuldak.domain.admin.QAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface AdminUserRepository extends
        JpaRepository<AdminUser, Long>,
        AdminUserRepositoryCustom,
        QuerydslPredicateExecutor<AdminUser>,
        QuerydslBinderCustomizer<QAdminUser>
{
    @Override
    default void customize(QuerydslBindings bindings, QAdminUser root) {
        bindings.excludeUnlistedProperties(true);
    }
}