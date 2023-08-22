package com.sulsul.suldaksuldak.dto.reservation;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReservationUserRes {
    Long id;
    String userEmail;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static ReservationUserRes from (
            ReservationUserDto reservationUserDto
    ) {
        return new ReservationUserRes(
                reservationUserDto.getId(),
                reservationUserDto.getUserEmail(),
                reservationUserDto.getCreatedAt(),
                reservationUserDto.getModifiedAt()
        );
    }
}
