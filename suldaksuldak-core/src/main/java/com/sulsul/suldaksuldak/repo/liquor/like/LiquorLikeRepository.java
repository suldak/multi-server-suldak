package com.sulsul.suldaksuldak.repo.liquor.like;

import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.domain.liquor.QLiquorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorLikeRepository extends
        JpaRepository<LiquorLike, Long>,
        LiquorLikeRepositoryCustom,
        QuerydslPredicateExecutor<LiquorLike>,
        QuerydslBinderCustomizer<QLiquorLike>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorLike root) {
        bindings.excludeUnlistedProperties(true);
    }
}
