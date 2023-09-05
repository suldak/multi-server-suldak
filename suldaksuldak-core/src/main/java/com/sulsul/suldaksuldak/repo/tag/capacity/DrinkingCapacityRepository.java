package com.sulsul.suldaksuldak.repo.tag.capacity;

import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.QDrinkingCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface DrinkingCapacityRepository extends
        JpaRepository<DrinkingCapacity, Long>,
        DrinkingCapacityRepositoryCustom,
        QuerydslPredicateExecutor<DrinkingCapacity>,
        QuerydslBinderCustomizer<QDrinkingCapacity>
{
    @Override
    default void customize(QuerydslBindings bindings, QDrinkingCapacity root) {
        bindings.excludeUnlistedProperties(true);
    }
}
