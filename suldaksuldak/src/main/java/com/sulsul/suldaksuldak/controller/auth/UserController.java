package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.component.auth.ToKakao;
import com.sulsul.suldaksuldak.component.auth.ToNaver;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.dto.auth.UserReq;
import com.sulsul.suldaksuldak.service.auth.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ParameterType;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
@Api(tags = "[MAIN] 로그인 및 토큰 관리")
public class UserController {
    private final UserService userService;
    private final ToKakao toKakao;
    private final ToNaver toNaver;

    @ApiOperation(
            value = "회원가입",
            notes = "소셜 회원가입 및 차제 회원가입"
    )
    @PostMapping(value = "/signup")
    public ApiDataResponse<Boolean> signup(
            @RequestBody UserReq userReq
    ) {
        Boolean result = false;
        if (userReq.getRegistration().equals(Registration.KAKAO)) {
            String accessToken = toKakao.getAccessToken(userReq.getCode());
            UserDto userDto = toKakao.getUserInfo(accessToken);
            log.info(userDto.toString());
            result = userService.createUser(userDto);
        } else if (userReq.getRegistration().equals(Registration.GOOGLE)) {
            // TODO GOOGLE
        } else if (userReq.getRegistration().equals(Registration.NAVER)) {
            String accessToken = toNaver.getAccessToken(userReq.getCode(), userReq.getState());
            UserDto userDto = toNaver.getUserInfo(accessToken);
            log.info(userDto.toString());
            result = userService.createUser(userDto);
        } else if (userReq.getRegistration().equals(Registration.APPLE)) {
            // TODO APPLE
        } else {
            result = userService.createUser(userReq.toDto());
        }

        return ApiDataResponse.of(
                result
        );
    }

    @ApiOperation(
            value = "자체 로그인",
            notes = "술닥술닥 자체 로그인"
    )
    @PostMapping(value = "/login")
    public void login(@RequestBody UserReq userReq) {

    }



//    @GetMapping(value = "/kakao")
//    public ApiDataResponse<Boolean> kakaoAuth(
//            @RequestParam String code,
//            @RequestParam String state
//    ) {
//        String accessToken = toKakao.getAccessToken(code);
//        UserDto userDto = toKakao.getUserInfo(accessToken);
//        return ApiDataResponse.of(
//                true
//        );
//    }
//
//    @GetMapping(value = "/naver")
//    public ApiDataResponse<Boolean> naverAuth(
//            @RequestParam String code,
//            @RequestParam String state
//    ) {
//        String accessToken = toNaver.getAccessToken(code, state);
//        UserDto userDto = toNaver.getUserInfo(accessToken);
//        return ApiDataResponse.of(true);
//    }
}
