package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.component.auth.ToKakao;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.service.auth.AuthService;
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
    private final AuthService authService;
    private final ToKakao toKakao;

    // kauth.kakao.com/oauth/authorize?client_id=c14c29936ab91bf8d5ff8ccf7ab0c06f&redirect_uri=http://localhost:8080/api/v1/oauth/kakao&response_type=code
    @GetMapping(value = "/kakao")
    public ApiDataResponse<Boolean> kakaoCallback(
            @RequestParam String code
    ) {
        log.info("code > {}", code);
        String accessToken = toKakao.getAccessToken(code);
        log.info("accessToken >> {}", accessToken);
        return ApiDataResponse.of(
                true
        );
    }
}
