package com.sulsul.suldaksuldak.repo.tag.material;

import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import com.sulsul.suldaksuldak.domain.tag.QLiquorMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MaterialTypeRepository extends
        JpaRepository<LiquorMaterial, Long>,
        MaterialTypeRepositoryCustom,
        QuerydslPredicateExecutor<LiquorMaterial>,
        QuerydslBinderCustomizer<QLiquorMaterial>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorMaterial root) {
        bindings.excludeUnlistedProperties(true);
    }
}
