package com.sulsul.suldaksuldak.service.auth;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.auth.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;

    public Boolean createUser(
            UserDto userDto
    ) {
        try {
            List<UserDto> userDtos = userRepository.findByEmailOrNickname(
                    userDto.getEmail(),
                    userDto.getNickname()
            );
            if (!userDtos.isEmpty()) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "이메일 또는 닉네임이 중복됩니다.");
            }
            if (userDto.getId() == null) {
                userRepository.save(
                        userDto.toEntity()
                );
            } else {
                userRepository.findById(userDto.getId())
                        .ifPresentOrElse(
                                user -> {
                                    userRepository.save(
                                            userDto.updateEntity(user)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.BAD_REQUEST, "수정할 유저를 찾을 수 없습니다.");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
