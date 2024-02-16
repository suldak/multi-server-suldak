package com.sulsul.suldaksuldak.service.admin;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {
    private final UserRepository userRepository;

    public Boolean updateUserData(
            UserDto userDto
    ) {
        try {
            if (userDto.getId() == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "Pri KEY is null");
            userRepository.findById(userDto.getId())
                    .ifPresentOrElse(
                            findUser -> {
                                userRepository.save(
                                        userDto.updateUser(findUser)
                                );
                            },
                            () -> {
                                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_USER);
                            }
                    );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
