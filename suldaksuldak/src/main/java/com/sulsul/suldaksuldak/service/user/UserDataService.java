package com.sulsul.suldaksuldak.service.user;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.dto.user.UserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDataService {
    private final FileService fileService;
    private final UserRepository userRepository;

    /**
     * 회원 닉네임 수정
     */
    public Boolean modifiedUserNickname(
            Long id,
            String nickname
    ) {
        try {
            if (id == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "Pri KEY is null");
            userRepository.findById(id)
                    .ifPresentOrElse(
                            user -> {
                                user.setNickname(nickname);
                                userRepository.save(user);
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
     * 회원 자기소개 수정
     */
    public Boolean modifiedUserSelfIntroduction(
            Long id,
            String selfIntroduction
    ) {
        try {
            if (id == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "Pri KEY is null");
            userRepository.findById(id)
                    .ifPresentOrElse(
                            user -> {
                                user.setSelfIntroduction(selfIntroduction);
                                userRepository.save(user);
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
     * 우저 사진 수정
     */
    public Boolean changeUserPicture(
            MultipartFile file,
            Long id
    ) {
        try {
            userRepository.findById(id)
                    .ifPresentOrElse(
                            findUser -> {
                                FileBase fileBase = fileService.saveFile(file);
                                if (fileBase == null) {
                                    throw new GeneralException(ErrorCode.INTERNAL_ERROR, "파일 저장에 문제가 있습니다.");
                                }
                                if (findUser.getFileBase() != null) {
                                    fileService.deleteFile(findUser.getFileBase().getFileNm());
                                }
                                userRepository.save(UserDto.updatePicture(findUser, fileBase));
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

    public Boolean checkUserNickname(
            String userNickname
    ) {
        try {
            return userRepository.findByNickname(userNickname).isEmpty();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean withdrawalUser(
            Long id
    ) {
        try {
            userRepository.findById(id)
                    .ifPresentOrElse(
                            findUser -> {
                                // 사진 삭제
//                                if (findUser.getFileBase() != null) {
//                                    fileService.deleteFile(findUser.getFileBase().getFileNm());
//                                }
                                userRepository.save(UserDto.updateUserActive(findUser, false));
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

    public Boolean updateUserAlarm(
            Long userPriKey,
            Boolean alarmActive,
            Boolean soundActive,
            Boolean vibrationActive,
            Boolean pushActive,
            Boolean marketingActive
    ) {
        try {
            userRepository.findById(userPriKey)
                    .ifPresentOrElse(
                            findUser -> {
                                findUser.setAlarmActive(alarmActive);
                                findUser.setSoundActive(soundActive);
                                findUser.setVibrationActive(vibrationActive);
                                findUser.setPushActive(pushActive);
                                findUser.setMarketingActive(marketingActive);
                                userRepository.save(findUser);
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
