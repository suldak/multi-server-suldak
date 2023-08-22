package com.sulsul.suldaksuldak.controller.reservation;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserDto;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserReq;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping(value = "/user")
    public ApiDataResponse<Boolean> createUser(
            @RequestBody ReservationUserReq reservationUserReq
    ) {
        return ApiDataResponse.of(
                reservationService.createUser(
                        reservationUserReq.toDto()
                )
        );
    }

    @GetMapping(value = "/user")
    public ApiDataResponse<List<ReservationUserRes>> getAllUserList() {
        return ApiDataResponse.of(
                reservationService.getAllReservationUserList()
                        .stream()
                        .map(ReservationUserRes::from)
                        .toList()
        );
    }

    @GetMapping(value = "/user-detail")
    public ApiDataResponse<ReservationUserRes> getUserDetail(
            String userEmail
    ) {
        Optional<ReservationUserDto> userDto =
                reservationService.getReservationUserByEmail(userEmail);
        if (userDto.isEmpty()) {
            throw new GeneralException(ErrorCode.NOT_FOUND, "해당 이메일의 유저가 없습니다.");
        }

        return ApiDataResponse.of(
                ReservationUserRes.from(userDto.get())
        );
    }
}
