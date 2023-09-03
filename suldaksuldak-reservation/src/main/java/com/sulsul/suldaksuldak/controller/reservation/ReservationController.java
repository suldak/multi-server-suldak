package com.sulsul.suldaksuldak.controller.reservation;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserDto;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserReq;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.reservation.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reservation")
@Api(tags = "[사전예약] ReservationController")
public class ReservationController {
    private final ReservationService reservationService;

    @ApiOperation(
            value = "사전예약 User 등록",
            notes = "User를 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 10000, message = "이메일이 공백이거나 이미 등록된 이메일일 경우"),
            @ApiResponse(code = 20002, message = "시스템 Error")
    })
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

    @ApiOperation(
            value = "사전예약 User 목록 반환",
            notes = "사전예약 완료한 User의 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 20002, message = "시스템 Error")
    })
    @GetMapping(value = "/user")
    public ApiDataResponse<List<ReservationUserRes>> getAllUserList() {
        return ApiDataResponse.of(
                reservationService.getAllReservationUserList()
                        .stream()
                        .map(ReservationUserRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "UserEmail에 해당하는 User 반환",
            notes = "UserEmail이 같은 User를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 10000, message = "이메일이 공백"),
            @ApiResponse(code = 20002, message = "시스템 Error")
    })
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
