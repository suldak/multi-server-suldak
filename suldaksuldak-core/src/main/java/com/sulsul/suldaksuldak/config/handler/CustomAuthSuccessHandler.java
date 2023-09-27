package com.sulsul.suldaksuldak.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.TokenMap;
import com.sulsul.suldaksuldak.dto.auth.TokenRes;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.dto.auth.UserRes;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        JSONObject jsonObject;

        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();

        Optional<UserDto> optionalUserDTO = userRepository.findByEmail(userId);

        if (optionalUserDTO.isPresent()) {
            TokenMap tokenMap = TokenUtils.getTokenMap(optionalUserDTO.get());

            ApiDataResponse<UserRes> userResApiDataResponse =
                    ApiDataResponse.of(
                            UserRes.from(
                                    optionalUserDTO.get(),
                                    TokenRes.from(tokenMap)
                            )
                    );

            ObjectMapper objectMapper = new ObjectMapper();
            Map result = objectMapper.convertValue(userResApiDataResponse, Map.class);

            jsonObject = new JSONObject(result);

//            userRepository.findById(optionalUserDTO.get().getId())
//                    .ifPresentOrElse(
//                            findUser -> {
//                                userRepository.save(
//                                        optionalUserDTO.get().updateLastAccess(findUser)
//                                );
//                            },
//                            () -> {
//                                log.error("ERROR");
//                            }
//                    );
//
//            response.addHeader(
//                    AccessToken.ACCESS_HEADER,
//                    AccessToken.TOKEN_TYPE + " " + tokenMap.getAccessToken());
//            response.addHeader(
//                    RefreshToken.REFRESH_HEADER,
//                    RefreshToken.TOKEN_TYPE + " " + tokenMap.getRefreshToken());
        } else {
            HashMap<String, Object> responseMap = new HashMap<>();
            responseMap.put("resultCode", 9001);
            responseMap.put("token", null);
            responseMap.put("failMsg", "계정 정보를 찾을 수 없습니다.");
            jsonObject = new JSONObject(responseMap);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);  // 최종 저장된 '사용자 정보', '사이트 정보' Front 전달
        printWriter.flush();
        printWriter.close();
    }
}
