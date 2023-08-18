package com.sulsul.suldaksuldak.component.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ToKakao {
    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String infoUrl;

    @Autowired
    public ToKakao() {
        HttpComponentsClientHttpRequestFactory factory
                = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);

        restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
    public String getAccessToken(
            String code
    ) {
        log.info(restApiKey);
        log.info(redirectUrl);
        log.info(tokenUrl);
        try {
            HttpEntity<MultiValueMap<String, String>> kakaoTokenReq =
                    new HttpEntity<>(getParams(code), getHeader());
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    kakaoTokenReq,
                    String.class
            );
            String tokenJson = response.getBody();
            JSONObject jsonObject = new JSONObject(tokenJson);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    public Map<String, Object> getUserInfo(
            String accessToken
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HttpEntity<MultiValueMap<String, String>> kakaoTokenReq =
                    new HttpEntity<>(getHeader(accessToken));
            ResponseEntity<String> response = restTemplate.exchange(
                    infoUrl,
                    HttpMethod.GET,
                    kakaoTokenReq,
                    String.class
            );
            String tokenJson = response.getBody();
            JSONObject jsonObject = new JSONObject(tokenJson);
            log.info(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Content-type",
                "application/x-www-form-urlencoded;charset=utf-8"
        );

        return headers;
    }

    private HttpHeaders getHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Content-type",
                "application/x-www-form-urlencoded;charset=utf-8"
        );
        headers.add(
                "Authorization",
                "Bearer " + accessToken
        );

        return headers;
    }

    private MultiValueMap<String, String> getParams(
            String code
    ) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // authorization_code로 고정
        params.add("grant_type", "authorization_code");
        // 앱 REST API 키
        params.add("client_id", restApiKey);
        // 인가 코드가 리다이렉트된 URI
        params.add("redirect_uri", redirectUrl);
        // 인가 코드 받기 요청으로 얻은 인가 코드
        params.add("code", code);

        return params;
    }
}
