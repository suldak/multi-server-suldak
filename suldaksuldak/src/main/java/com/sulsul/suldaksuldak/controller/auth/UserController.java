package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.dto.auth.UserReq;
import com.sulsul.suldaksuldak.dto.auth.UserRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.util.StringUtils.hasText;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {
    private final AuthService authService;

    @PostMapping(value = "/sign-up")
    public ApiDataResponse<Boolean> createUser(
            @RequestBody UserReq userReq
    ) {
        return ApiDataResponse.of(
                authService.createUser(userReq.toDto())
        );
    }

    @PostMapping(value = "/sign-in")
    public ApiDataResponse<UserRes> loginUser(
            @RequestBody UserReq userReq
    ) {
        if (!hasText(userReq.getEmail()) || !hasText(userReq.getPassword())) {
            throw new GeneralException(ErrorCode.BAD_REQUEST, "ID 또는 Password가 누락되었습니다.");
        }
        Optional<UserDto> user = authService.loginUser(
                userReq.getEmail(),
                userReq.getPassword()
        );
        if (user.isEmpty()) {
            throw new GeneralException(
                    ErrorCode.NOT_FOUND,
                    "해당 유저를 찾을 수 없습니다."
            );
        }
        return ApiDataResponse.of(
                UserRes.from(user.get())
        );
    }


}
