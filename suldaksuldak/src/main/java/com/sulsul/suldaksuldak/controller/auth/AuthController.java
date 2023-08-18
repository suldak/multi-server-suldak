package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.component.auth.ToKakao;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/oauth")
public class AuthController {
    private final ToKakao toKakao;
    @GetMapping(value = "/kakao")
    public ApiDataResponse<Boolean> kakaoAuth(
            @RequestParam String code,
            @RequestParam String state
    ) {
        String accessToken = toKakao.getAccessToken(code);
        log.info("accessToken >> {}", accessToken);
        Map<String, Object> objectMap = toKakao.getUserInfo(accessToken);

        return ApiDataResponse.of(
                true
        );
    }
}
