package com.sulsul.suldaksuldak.service.admin;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                throw new GeneralException(ErrorCode.BAD_REQUEST, "유저의 기본키 정보를 찾을 수 없습니다.");
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

    /**
     * 유저의 정지 기간을 수정합니다.
     */
    public Boolean modifiedUserStopDate(
            Long targetUserPriKey,
            LocalDateTime stopStartDate,
            LocalDateTime stopEndData
    ) {
        try {
            if (targetUserPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "유저의 기본키 정보를 찾을 수 없습니다.");
            if (stopStartDate == null || stopEndData == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "정지 시작 일자 및 정지 만료 일자를 입력해주세요.");
            LocalDateTime startDate = stopStartDate.truncatedTo(java.time.temporal.ChronoUnit.DAYS);
            LocalDateTime endDate = stopEndData.truncatedTo(java.time.temporal.ChronoUnit.DAYS);
            if (endDate.isBefore(startDate) || startDate.equals(endDate))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "날짜 범위가 유효하지 않습니다."
                );
            userRepository.findById(targetUserPriKey)
                    .ifPresentOrElse(
                            findUser -> {
                                findUser.setSuspensionStartDate(startDate);
                                findUser.setSuspensionEndDate(endDate);
                                userRepository.save(findUser);
                            },
                            () -> {
                                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_USER);
                            }
                    );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 유저의 정지 기간을 초기화 합니디.
     */
    public Boolean setNullUserStopDate(
            Long targetUserPriKey
    ) {
        try {
            if (targetUserPriKey == null)
                throw new GeneralException(ErrorCode.BAD_REQUEST, "유저의 기본키 정보를 찾을 수 없습니다.");
            userRepository.findById(targetUserPriKey)
                    .ifPresentOrElse(
                            findUser -> {
                                findUser.setSuspensionStartDate(null);
                                findUser.setSuspensionEndDate(null);
                                userRepository.save(findUser);
                            },
                            () -> {
                                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_USER);
                            }
                    );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}
