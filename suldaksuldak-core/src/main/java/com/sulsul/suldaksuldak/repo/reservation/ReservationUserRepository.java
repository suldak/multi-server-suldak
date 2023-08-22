package com.sulsul.suldaksuldak.repo.reservation;

import com.sulsul.suldaksuldak.domain.user.QReservationUser;
import com.sulsul.suldaksuldak.domain.user.ReservationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ReservationUserRepository extends
        JpaRepository<ReservationUser, Long>,
        ReservationUserRepositoryCustom,
        QuerydslPredicateExecutor<ReservationUser>,
        QuerydslBinderCustomizer<QReservationUser>
{
    @Override
    default void customize(QuerydslBindings bindings, QReservationUser root) {
        bindings.excludeUnlistedProperties(true);
    }
}
