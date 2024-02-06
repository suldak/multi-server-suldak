package com.sulsul.suldaksuldak.repo.party.comment;

import com.sulsul.suldaksuldak.domain.party.PartyComment;
import com.sulsul.suldaksuldak.domain.party.QPartyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyCommentRepository extends
        JpaRepository<PartyComment, String>,
        PartyCommentRepositoryCustom,
        QuerydslPredicateExecutor<PartyComment>,
        QuerydslBinderCustomizer<QPartyComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartyComment root) {
        bindings.excludeUnlistedProperties(true);
    }
}
