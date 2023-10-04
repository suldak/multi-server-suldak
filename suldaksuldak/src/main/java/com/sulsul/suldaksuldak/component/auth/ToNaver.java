package com.sulsul.suldaksuldak.component.auth;

import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.auth.SocialUserDto;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.tool.UtilTool;
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

@Component
@Slf4j
public class ToNaver {
    // https://developers.naver.com/docs/login/profile/profile.md
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String restApiKey;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String infoUrl;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Autowired
    public ToNaver() {
        HttpComponentsClientHttpRequestFactory factory
                = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);
        factory.setBufferRequestBody(false);

        restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String getAccessToken (
            String code,
            String state
    ) {
        try {
            HttpEntity<MultiValueMap<String, String>> naverTokenReq =
                    new HttpEntity<>(getParams(code, state), getHeader());
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    naverTokenReq,
                    String.class
            );
            String tokenJson = response.getBody();
//            log.info(tokenJson);
            JSONObject jsonObject = new JSONObject(tokenJson);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    public SocialUserDto getUserInfo(
            String accessToken
    ) {
        try {
            HttpEntity<MultiValueMap<String, String>> naverTokenReq =
                    new HttpEntity<>(getHeader(accessToken));
            ResponseEntity<String> response = restTemplate.exchange(
                    infoUrl,
                    HttpMethod.GET,
                    naverTokenReq,
                    String.class
            );
            String tokenJson = response.getBody();
            JSONObject jsonObject = new JSONObject(tokenJson);
//            log.info(tokenJson);
            return SocialUserDto.of(
                    jsonObject.getJSONObject("response").getString("email"),
                    jsonObject.getJSONObject("response").getString("id"),
                    jsonObject.getJSONObject("response").isNull("nickname") ? null :
                            jsonObject.getJSONObject("response").getString("nickname"),
                    Registration.NAVER
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            String code,
            String state
    ) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", restApiKey);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);
        return params;
    }
}
