package com.sulsul.suldaksuldak.repo.liquor.abv;

import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.QLiquorAbv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorAbvRepository extends
        JpaRepository<LiquorAbv, Long>,
        LiquorAbvRepositoryCustom,
        QuerydslPredicateExecutor<LiquorAbv>,
        QuerydslBinderCustomizer<QLiquorAbv>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorAbv root) {
        bindings.excludeUnlistedProperties(true);
    }
}
