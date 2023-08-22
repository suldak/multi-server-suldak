package com.sulsul.suldaksuldak.repo.reservation;

import com.sulsul.suldaksuldak.dto.reservation.ReservationUserDto;

import java.util.List;
import java.util.Optional;

public interface ReservationUserRepositoryCustom {
    Optional<ReservationUserDto> findByUserEmail(
            String userEmail
    );
    List<ReservationUserDto> findAllReservationUserList();
}
