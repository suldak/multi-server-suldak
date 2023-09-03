package com.sulsul.suldaksuldak.dto.reservation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "사전예약 User Req")
public class ReservationUserReq {
    @ApiModelProperty(value = "사용자 email", required = true)
    String userEmail;

    public ReservationUserDto toDto() {
        return ReservationUserDto.of(userEmail);
    }
}
