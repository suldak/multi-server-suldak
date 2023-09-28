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
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ToGoogle {
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String authorizationUrl;
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUrl;
    @Value("${spring.security.oauth2.client.provider.google.resource-uri}")
    private String resourceUrl;

    @Autowired
    public ToGoogle() {
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
            String code
    ) {
        try {
            HttpEntity<String> googleTokenReq =
                    new HttpEntity<>(getParams(code), getHeader());

            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    googleTokenReq,
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

    public SocialUserDto getUserInfo(String accessToken) {
        try {
            HttpEntity<HttpHeaders> googleTokenReq =
                    new HttpEntity<>(getHeader(accessToken));
            ResponseEntity<String> response = restTemplate.exchange(
                    resourceUrl,
                    HttpMethod.GET,
                    googleTokenReq,
                    String.class
            );
            String tokenJson = response.getBody();
//            log.info(tokenJson);
            JSONObject jsonObject = new JSONObject(tokenJson);
            return SocialUserDto.of(
                    jsonObject.getString("email"),
                    jsonObject.getString("id"),
                    jsonObject.getString("given_name"),
                    Registration.GOOGLE
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Content-type",
                "application/x-www-form-urlencoded"
        );

        return headers;
    }

    private HttpHeaders getHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
//        headers.add(
//                "Content-type",
//                "application/x-www-form-urlencoded;charset=utf-8"
//        );
        headers.add(
                "Authorization",
                "Bearer " + accessToken
        );

        return headers;
    }

    private String getParams(
            String code
    ) {
        return "code=" + code + "&" +
                "client_id=" + clientId + "&" +
                "client_secret=" + clientSecret + "&" +
                "redirect_uri=" + redirectUrl + "&" +
                "grant_type=authorization_code";
    }
}
