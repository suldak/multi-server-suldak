package com.sulsul.suldaksuldak.repo.tag.material;

import com.sulsul.suldaksuldak.domain.tag.MaterialType;
import com.sulsul.suldaksuldak.domain.tag.QMaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface MaterialTypeRepository extends
        JpaRepository<MaterialType, Long>,
        MaterialTypeRepositoryCustom,
        QuerydslPredicateExecutor<MaterialType>,
        QuerydslBinderCustomizer<QMaterialType>
{
    @Override
    default void customize(QuerydslBindings bindings, QMaterialType root) {
        bindings.excludeUnlistedProperties(true);
    }
}
