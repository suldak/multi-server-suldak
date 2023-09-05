package com.sulsul.suldaksuldak.repo.tag.sell;

import com.sulsul.suldaksuldak.domain.tag.LiquorSell;
import com.sulsul.suldaksuldak.domain.tag.QLiquorSell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorSellRepository extends
        JpaRepository<LiquorSell, Long>,
        LiquorSellRepositoryCustom,
        QuerydslPredicateExecutor<LiquorSell>,
        QuerydslBinderCustomizer<QLiquorSell>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorSell root) {
        bindings.excludeUnlistedProperties(true);
    }
}
