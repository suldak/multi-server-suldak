package com.sulsul.suldaksuldak.service.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDto checkAccess(String refreshToken) {
        try {
            Optional<UserDto> optionalUserDTO =
                    TokenUtils.getUserDTOFromRefreshToken(
                            TokenUtils.getTokenFromHeader(refreshToken)
                    );

            if (optionalUserDTO.isEmpty()) {
                throw new GeneralException(ErrorCode.REFRESH_TOKEN_EXPIRATION, "Refresh Token이 만료되었습니다");
            } else {
                return optionalUserDTO.get();
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
