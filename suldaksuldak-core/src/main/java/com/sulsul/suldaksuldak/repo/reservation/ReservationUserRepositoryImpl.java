package com.sulsul.suldaksuldak.repo.reservation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.sulsul.suldaksuldak.domain.user.QReservationUser.reservationUser;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationUserRepositoryImpl
        implements ReservationUserRepositoryCustom
{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ReservationUserDto> findByUserEmail(String userEmail) {
        return Optional.ofNullable(
                getReservationUserDtoQuery()
                        .from(reservationUser)
                        .where(
                                reservationUser.userEmail.eq(userEmail)
                        )
                        .fetchFirst()
        );
    }

    @Override
    public List<ReservationUserDto> findAllReservationUserList() {
        return getReservationUserDtoQuery()
                .from(reservationUser)
                .fetch();
    }

    private JPAQuery<ReservationUserDto> getReservationUserDtoQuery() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReservationUserDto.class,
                                reservationUser.id,
                                reservationUser.userEmail,
                                reservationUser.createdAt,
                                reservationUser.modifiedAt
                        )
                );
    }
}
