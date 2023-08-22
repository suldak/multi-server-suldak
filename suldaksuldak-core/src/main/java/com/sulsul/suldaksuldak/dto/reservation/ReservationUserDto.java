package com.sulsul.suldaksuldak.dto.reservation;

import com.sulsul.suldaksuldak.domain.user.ReservationUser;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReservationUserDto {
    Long id;
    String userEmail;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static ReservationUserDto of (
            String userEmail
    ) {
        return new ReservationUserDto(
                null,
                userEmail,
                null,
                null
        );
    }

    public ReservationUser toEntity() {
        return ReservationUser.of(
                id,
                userEmail
        );
    }
}
