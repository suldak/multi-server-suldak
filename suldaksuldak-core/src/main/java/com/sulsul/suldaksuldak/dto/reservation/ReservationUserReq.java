package com.sulsul.suldaksuldak.dto.reservation;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ReservationUserReq {
    String userEmail;

    public ReservationUserDto toDto() {
        return ReservationUserDto.of(userEmail);
    }
}
