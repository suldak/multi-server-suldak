package com.sulsul.suldaksuldak.repo.question.answer;

import com.sulsul.suldaksuldak.domain.question.LiquorAnswer;
import com.sulsul.suldaksuldak.domain.question.QLiquorAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorAnswerRepository extends
        JpaRepository<LiquorAnswer, Long>,
        LiquorAnswerRepositoryCustom,
        QuerydslPredicateExecutor<LiquorAnswer>,
        QuerydslBinderCustomizer<QLiquorAnswer>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorAnswer root) {
        bindings.excludeUnlistedProperties(true);
    }
}
