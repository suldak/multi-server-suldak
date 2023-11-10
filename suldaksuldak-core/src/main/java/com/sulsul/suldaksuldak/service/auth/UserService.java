package com.sulsul.suldaksuldak.service.auth;

import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.dto.search.UserSearchReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.tool.TokenUtils;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
            // 탈퇴한 유저 로그인 방지
            if (!optionalUserDto.get().getIsActive())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_USER);
            return User.builder()
                    .username(optionalUserDto.get().getUserEmail())
                    .password(optionalUserDto.get().getUserPw())
                    .authorities(Collections.emptyList())
                    .build();
        } else {
            throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_USER);
        }
    }

    /**
     * 토큰 조회
     */
    public UserDto checkAccess(String refreshToken) {
        try {
            Optional<UserDto> optionalUserDTO =
                    TokenUtils.getUserDTOFromRefreshToken(refreshToken);

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
            userRepository.save(userDto.toEntity(null));
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
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

    public Boolean login(
            String userEmail,
            String userPw
    ) {
        try {
            Optional<UserDto> optionalUserDto = userRepository.findUserBySocial(
                    userEmail,
                    UtilTool.encryptPassword(userPw, userEmail),
                    Registration.KAKAO
            );
            if (optionalUserDto.isPresent()) {
                log.info(optionalUserDto.get().toString());
                return true;
            } else {
                return false;
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public List<UserDto> getUserDtoList(
            UserSearchReq userSearchReq
    ) {
        try {
            return userRepository.findByOptions(
                    userSearchReq.getUserEmail(),
                    userSearchReq.getNickname(),
                    userSearchReq.getGender(),
                    userSearchReq.getBirthdayYear(),
                    userSearchReq.getStartYear(),
                    userSearchReq.getEndYear(),
                    userSearchReq.getRegistration(),
                    userSearchReq.getLevelList(),
                    userSearchReq.getWarningCntList(),
                    userSearchReq.getIsActive()
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
