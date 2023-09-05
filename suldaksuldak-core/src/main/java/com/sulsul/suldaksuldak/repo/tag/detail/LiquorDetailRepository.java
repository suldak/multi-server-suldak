package com.sulsul.suldaksuldak.repo.tag.detail;

import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.QLiquorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorDetailRepository extends
        JpaRepository<LiquorDetail, Long>,
        LiquorDetailRepositoryCustom,
        QuerydslPredicateExecutor<LiquorDetail>,
        QuerydslBinderCustomizer<QLiquorDetail>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorDetail root) {
        bindings.excludeUnlistedProperties(true);
    }
}
