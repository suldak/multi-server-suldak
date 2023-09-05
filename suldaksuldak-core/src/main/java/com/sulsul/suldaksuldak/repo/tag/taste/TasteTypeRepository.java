package com.sulsul.suldaksuldak.repo.tag.taste;

import com.sulsul.suldaksuldak.domain.tag.QTasteType;
import com.sulsul.suldaksuldak.domain.tag.TasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface TasteTypeRepository extends
        JpaRepository<TasteType, Long>,
        TasteTypeRepositoryCustom,
        QuerydslPredicateExecutor<TasteType>,
        QuerydslBinderCustomizer<QTasteType>
{
    @Override
    default void customize(QuerydslBindings bindings, QTasteType root) {
        bindings.excludeUnlistedProperties(true);
    }
}
