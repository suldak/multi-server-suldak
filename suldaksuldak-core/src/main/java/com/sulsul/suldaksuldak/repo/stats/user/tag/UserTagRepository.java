package com.sulsul.suldaksuldak.repo.stats.user.tag;

import com.sulsul.suldaksuldak.domain.stats.QUserTag;
import com.sulsul.suldaksuldak.domain.stats.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserTagRepository extends
        JpaRepository<UserTag, String>,
        UserTagRepositoryCustom,
        QuerydslPredicateExecutor<UserTag>,
        QuerydslBinderCustomizer<QUserTag>
{
    @Override
    default void customize(QuerydslBindings bindings, QUserTag root) {
        bindings.excludeUnlistedProperties(true);
    }
}
