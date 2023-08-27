package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.component.auth.ToKakao;
import com.sulsul.suldaksuldak.component.auth.ToNaver;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/oauth")
public class AuthController {
    private final ToKakao toKakao;
    private final ToNaver toNaver;

    @GetMapping(value = "/kakao")
    public ApiDataResponse<Boolean> kakaoAuth(
            @RequestParam String code,
            @RequestParam String state
    ) {
        String accessToken = toKakao.getAccessToken(code);
        UserDto userDto = toKakao.getUserInfo(accessToken);
        return ApiDataResponse.of(
                true
        );
    }

    @GetMapping(value = "/naver")
    public ApiDataResponse<Boolean> naverAuth(
            @RequestParam String code,
            @RequestParam String state
    ) {
        String accessToken = toNaver.getAccessToken(code, state);
        UserDto userDto = toNaver.getUserInfo(accessToken);
        return ApiDataResponse.of(true);
    }
}
