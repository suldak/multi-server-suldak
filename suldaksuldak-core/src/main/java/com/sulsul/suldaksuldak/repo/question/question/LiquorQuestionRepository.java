package com.sulsul.suldaksuldak.repo.question.question;

import com.sulsul.suldaksuldak.domain.question.LiquorQuestion;
import com.sulsul.suldaksuldak.domain.question.QLiquorQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface LiquorQuestionRepository extends
        JpaRepository<LiquorQuestion, Long>,
        LiquorQuestionRepositoryCustom,
        QuerydslPredicateExecutor<LiquorQuestion>,
        QuerydslBinderCustomizer<QLiquorQuestion>
{
    @Override
    default void customize(QuerydslBindings bindings, QLiquorQuestion root) {
        bindings.excludeUnlistedProperties(true);
    }
}
