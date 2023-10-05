package com.sulsul.suldaksuldak.service.user;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.cut.CutOffUserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.cut.CutOffUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CutOffUserService {
    private final UserRepository userRepository;
    private final CutOffUserRepository cutOffUserRepository;

    /**
     * 유저 차단하기
     */
    public Boolean createCutOffUser(
            Long userId,
            Long cutUserId
    ) {
        try {
            if (userId == null || cutUserId == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "PriKey is NULL");
            }
            List<Long> findCutOffUserList = cutOffUserRepository.findByUserIdAndCutUserId(
                    userId,
                    cutUserId
            );
            if (!findCutOffUserList.isEmpty()) return true;
            userRepository.findById(userId)
                    .ifPresentOrElse(
                            findUser -> {
                                userRepository.findById(cutUserId)
                                        .ifPresentOrElse(
                                                findCutUser -> {
                                                    cutOffUserRepository.save(
                                                            CutOffUserDto.toEntity(
                                                                    findUser,
                                                                    findCutUser
                                                            )
                                                    );
                                                },
                                                () -> {
                                                    throw new GeneralException(
                                                            ErrorCode.NOT_FOUND,
                                                            "차단 할 유저를 찾지 못했습니다."
                                                    );
                                                }
                                        );
                            },
                            () -> {
                                throw new GeneralException(ErrorCode.NOT_FOUND, "유저를 찾지 못했습니다.");
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
     * 차당한 유저 목록 조회
     */
    public List<CutOffUserDto> getCutOffUserList(
            Long userId
    ) {
        try {
            return cutOffUserRepository.findByUserId(userId);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 유저 차단 해제
     */
    public Boolean deleteCutOffUser(
            Long userId,
            Long cutUserId
    ) {
        try {
            List<Long> cutUserIdList = cutOffUserRepository.findByUserIdAndCutUserId(
                    userId,
                    cutUserId
            );

            if (cutUserIdList.isEmpty()) return true;
            for (Long id: cutUserIdList) {
                cutOffUserRepository.deleteById(id);
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
