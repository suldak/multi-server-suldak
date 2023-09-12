package com.sulsul.suldaksuldak.repo.liquor.liquor;

import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.QLiquor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorRepository extends
        JpaRepository<Liquor, Long>,
        LiquorRepositoryCustom,
        QuerydslPredicateExecutor<Liquor>,
        QuerydslBinderCustomizer<QLiquor>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquor root) {
        bindings.excludeUnlistedProperties(true);
    }
}
