package com.sulsul.suldaksuldak.repo.party.schedule;

import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import com.sulsul.suldaksuldak.domain.party.batch.QPartySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PartyScheduleRepository extends
        JpaRepository<PartySchedule, Long>,
        PartyScheduleRepositoryCustom,
        QuerydslPredicateExecutor<PartySchedule>,
        QuerydslBinderCustomizer<QPartySchedule>
{
    @Override
    default void customize(QuerydslBindings bindings, QPartySchedule root) {
        bindings.excludeUnlistedProperties(true);
    }
}
