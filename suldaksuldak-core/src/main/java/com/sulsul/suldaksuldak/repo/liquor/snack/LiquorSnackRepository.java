package com.sulsul.suldaksuldak.repo.liquor.snack;

import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
import com.sulsul.suldaksuldak.domain.tag.QLiquorSnack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorSnackRepository extends
        JpaRepository<LiquorSnack, Long>,
        LiquorSnackRepositoryCustom,
        QuerydslPredicateExecutor<LiquorSnack>,
        QuerydslBinderCustomizer<QLiquorSnack>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorSnack root) {
        bindings.excludeUnlistedProperties(true);
    }
}
