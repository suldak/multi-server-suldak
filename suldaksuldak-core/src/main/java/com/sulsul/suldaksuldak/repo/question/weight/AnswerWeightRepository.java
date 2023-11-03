package com.sulsul.suldaksuldak.repo.question.weight;

import com.sulsul.suldaksuldak.domain.question.AnswerWeight;
import com.sulsul.suldaksuldak.domain.question.QAnswerWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface AnswerWeightRepository extends
        JpaRepository<AnswerWeight, Long>,
        AnswerWeightRepositoryCustom,
        QuerydslPredicateExecutor<AnswerWeight>,
        QuerydslBinderCustomizer<QAnswerWeight>
{
    @Override
    default void customize(QuerydslBindings bindings, QAnswerWeight root) {
        bindings.excludeUnlistedProperties(true);
    }
}
