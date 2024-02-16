package com.sulsul.suldaksuldak.config.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 아이디와 비밀번호 기반의 데이터를 Form 데이터로 전송을 받아 '인증'을 담당하는 필터입니다.
 */
@Slf4j
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode,
            String errorMessage
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentType("application/json;charset=UTF-8");

        log.error(errorCode.getMessage());
        try{
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                            ApiDataResponse.of(
                                    errorCode,
                                    errorMessage
                            )
                    ));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 지정된 URL로 form 전송을 하였을 경우 파라미터 정보를 가져온다.
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return Authentication {}
     * @throws AuthenticationException {}
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (GeneralException e) {
//            e.printStackTrace();
//            throw new GeneralException(e.getErrorCode(), e.getMessage());
            setErrorResponse(response, e.getErrorCode(), e.getMessage());
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR, e);
        }

    }


    /**
     * Request로 받은 ID와 패스워드 기반으로 토큰을 발급한다.
     *
     * @param request HttpServletRequest
     * @return UsernamePasswordAuthenticationToken
     * @throws Exception e
     */
    private UsernamePasswordAuthenticationToken getAuthRequest(
            HttpServletRequest request
    ) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            UserDto user = objectMapper.readValue(request.getInputStream(), UserDto.class);
            // ID와 패스워드를 기반으로 토큰 발급
            return new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPw());
        } catch (UsernameNotFoundException ae) {
            ae.printStackTrace();
//            throw new UsernameNotFoundException(ae.getMessage());
            throw new GeneralException(ErrorCode.NOT_FOUND, ae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }
}
