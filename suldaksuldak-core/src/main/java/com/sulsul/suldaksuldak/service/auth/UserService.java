package com.sulsul.suldaksuldak.service.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserDto> optionalUserDto =
                userRepository.findByUserEmail(userId);

        if (optionalUserDto.isPresent()) {
            return User.builder()
                    .username(optionalUserDto.get().getUserEmail())
                    .password(optionalUserDto.get().getUserPw())
                    .authorities(Collections.emptyList())
                    .build();
        } else {
            throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_USER);
        }
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

    /**
     * 회원 생성
     */
    public Boolean createUser(
            UserDto userDto
    ) {
        try {
            Optional<UserDto> checkEmail = userRepository.findByUserEmail(userDto.getUserEmail());
            if (checkEmail.isPresent()) throw new GeneralException(ErrorCode.BAD_REQUEST, "이미 가입된 이메일 입니다.");
            Optional<UserDto> checkNickname = userRepository.findByNickname(userDto.getNickname());
            if (checkNickname.isPresent()) throw new GeneralException(ErrorCode.BAD_REQUEST, "닉네임이 중복됩니다.");
            userRepository.save(userDto.toEntity());
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    public Optional<UserDto> getUserEmail(
            String userEmail
    ) {
        try {
            return userRepository.findByUserEmail(userEmail);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
