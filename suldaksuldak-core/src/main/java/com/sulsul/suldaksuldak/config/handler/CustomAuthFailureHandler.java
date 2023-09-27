package com.sulsul.suldaksuldak.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        // [STEP1] 클라이언트로 전달 할 응답 값을 구성합니다.
        JSONObject jsonObject = new JSONObject();
        String failMsg = "로그인 에러";

        // [STEP2] 발생한 Exception 에 대해서 확인합니다.
//        if (exception instanceof AuthenticationServiceException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//
//        } else if (exception instanceof BadCredentialsException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//
//        } else if (exception instanceof LockedException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//
//        } else if (exception instanceof DisabledException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//
//        } else if (exception instanceof AccountExpiredException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//
//        } else if (exception instanceof CredentialsExpiredException) {
//            failMsg = "로그인 정보가 일치하지 않습니다.";
//        }
        // [STEP4] 응답 값을 구성하고 전달합니다.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        ApiDataResponse<Objects> userResApiDataResponse = ApiDataResponse.of(
                ErrorCode.ACCESS_TOKEN_EXPIRATION,
                failMsg
        );
        ObjectMapper objectMapper = new ObjectMapper();
        Map result = objectMapper.convertValue(userResApiDataResponse, Map.class);
        jsonObject = new JSONObject(result);

        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
