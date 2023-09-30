package com.sulsul.suldaksuldak.repo.admin.consent;

import com.sulsul.suldaksuldak.domain.admin.ConsentItem;
import com.sulsul.suldaksuldak.domain.admin.QConsentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ConsentItemRepository extends
        JpaRepository<ConsentItem, Long>,
        ConsentItemRepositoryCustom,
        QuerydslPredicateExecutor<ConsentItem>,
        QuerydslBinderCustomizer<QConsentItem>
{
    @Override
    default void customize(QuerydslBindings bindings, QConsentItem root) {
        bindings.excludeUnlistedProperties(true);
    }
}
