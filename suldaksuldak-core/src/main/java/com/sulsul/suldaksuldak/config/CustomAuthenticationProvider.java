package com.sulsul.suldaksuldak.config;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private UserDetailsService userDetailsService;

    @NonNull
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(
            Authentication authentication
    ) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw;
        try {
            userPw = UtilTool.encryptPassword((String) token.getCredentials(), userId);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR, "PW encoding Failed");
        }
        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        if (!(userDetails.getPassword().equalsIgnoreCase(userPw) &&
                userDetails.getUsername().equalsIgnoreCase(userId))
        ) {
//            throw new GeneralException(ErrorCode.ERROR_AUTH, userDetails.getUsername() + " Invalid password");
            throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_USER.getMessage());
//            throw new BadCredentialsException(userDetails.getUsername() + " Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userPw,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
