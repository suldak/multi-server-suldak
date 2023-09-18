package com.sulsul.suldaksuldak.repo.liquor.name;

import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.domain.tag.QLiquorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorNameRepository extends
        JpaRepository<LiquorName, Long>,
        LiquorNameRepositoryCustom,
        QuerydslPredicateExecutor<LiquorName>,
        QuerydslBinderCustomizer<QLiquorName>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorName root) {
        bindings.excludeUnlistedProperties(true);
    }
}
