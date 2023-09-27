package com.sulsul.suldaksuldak.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulsul.suldaksuldak.constant.auth.SDTokken;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.ApiErrorResponse;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.service.auth.UserService;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.NonNull;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Value("${jwt.key}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Content-Type", "*");

        if (request.getRequestURI().startsWith("/swagger") || request.getRequestURI().startsWith("/v3")) {
            chain.doFilter(request, response);
            return;
        }

    //    if (request.getRequestURI().startsWith("/api/open")) {
    //        String accessKey = request.getHeader(RefreshToken.ACCESS_KEY_HEADER);
    //        if (accessKey == null) throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR);
    //        Optional<UserDTO> optionalUserDTO = userRepository.findByAccessKey(accessKey);
    //        if (optionalUserDTO.isEmpty()) throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR);
    //        request.setAttribute(RefreshToken.ReqUserNm, optionalUserDTO.get().getUserNm());
    //        request.setAttribute(RefreshToken.ReqUserPriKey, optionalUserDTO.get().getId());
    //        chain.doFilter(request, response);
    //        return;
    //    }

        // 1. 토큰이 필요하지 않은 API URL에 대해서 배열로 구성합니다.
        List<String> list = Arrays.asList(
                "/api/auth/login",
                "/api/auth/reissue-token"
    //            "/api/auth/soojibee-access"
        );

        // 2. 토큰이 필요하지 않은 API URL의 경우 => 로직 처리 없이 다음 필터로 이동
        if (list.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        // 3. OPTIONS 요청일 경우 => 로직 처리 없이 다음 필터로 이동
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        // [STEP1] Client에서 API를 요청할때 Header를 확인합니다.
    //        String header = request.getHeader(RefreshToken.REFRESH_HEADER);
    //        logger.debug("[+] header Check: " + header);
        try {
            String refreshHeader = request.getHeader(SDTokken.REFRESH_HEADER.getText());
            if (refreshHeader == null || refreshHeader.isBlank() || refreshHeader.equals("null"))
                throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR);
            if (refreshHeader.equals(jwtKey) || refreshHeader.split(" ")[1].equals(jwtKey)) {
                chain.doFilter(request, response);
                return;
            }
            UserDto userDto = userService.checkAccess(refreshHeader);
//            request.setAttribute(BkTokken.REQ_COMPANY_ID_LIST.getText(), userDto.getCompanyIdList());

    //        logger.debug("---------------------------------");
    //        logger.debug("[+] url: " + request.getRequestURI());
    //        logger.debug("[+] RefreshToken: " + refreshHeader);
    //        logger.debug("[+] refreshHeader: " + refreshHeader);
    //        logger.debug("---------------------------------");

            if (!refreshHeader.equalsIgnoreCase("")) {
                String refreshToken = TokenUtils.getTokenFromHeader(refreshHeader);
                // access token 과 refresh token 이 모두 유효한 경우
                if (TokenUtils.isValidToken(refreshToken)) {
                    chain.doFilter(request, response);
                } else {
                    throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR, "TOKEN is invalid");
                }
            }
            else {
                throw new GeneralException(ErrorCode.BUSINESS_EXCEPTION_ERROR, "Token is null");
            }
        } catch (GeneralException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    /**
     * 토큰 관련 Exception 발생 시 예외 응답값 구성
     *
     * @param e Exception
     * @return JSONObject
     */
    private JSONObject jsonResponseWrapper(Exception e) {

        String resultMsg = "";
        // JWT 토큰 만료
        if (e instanceof ExpiredJwtException) {
            resultMsg = "TOKEN Expired";
        }
        // JWT 허용된 토큰이 아님
        else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }

//        HashMap<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("status", 401);
//        jsonMap.put("code", "9999");
//        jsonMap.put("message", resultMsg);
//        jsonMap.put("reason", e.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();

        ApiDataResponse<Object> response =
                ApiDataResponse.of(
                        ErrorCode.ACCESS_TOKEN_EXPIRATION,
                        null
                );

        Map result = objectMapper.convertValue(response, Map.class);
        //        logger.error(resultMsg, e);
        return new JSONObject(result);
    }

    /**
     * 토큰 관련 Exception 발생 시 예외 응답값 구성
     *
     * @param e Exception
     * @return JSONObject
     */
    private JSONObject jsonResponseWrapper(GeneralException e) {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiErrorResponse response =
                ApiErrorResponse.of(
                        false,
                        e.getErrorCode(),
                        e.getMessage()
                );

        Map result = objectMapper.convertValue(response, Map.class);
        //        logger.error(resultMsg, e);
        return new JSONObject(result);
    }
}
