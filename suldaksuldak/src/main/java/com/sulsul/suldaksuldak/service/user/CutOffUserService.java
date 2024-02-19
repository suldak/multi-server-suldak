package com.sulsul.suldaksuldak.service.user;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.cut.CutOffUserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.cut.CutOffUserRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CutOffUserService {
    private final CheckPriKeyService checkPriKeyService;
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
                throw new GeneralException(ErrorCode.BAD_REQUEST, "기본키 정보가 누락되었습니다.");
            }
            List<Long> findCutOffUserList = cutOffUserRepository.findByUserIdAndCutUserId(
                    userId,
                    cutUserId
            );
            if (!findCutOffUserList.isEmpty()) return true;
            User user = checkPriKeyService.checkAndGetUser(userId);
            User cutUser = checkPriKeyService.checkAndGetUser(cutUserId);
            cutOffUserRepository.save(
                    CutOffUserDto.toEntity(
                            user,
                            cutUser
                    )
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
