package com.sulsul.suldaksuldak.service.stats;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.stats.search.LiquorSearchLogRepository;
import com.sulsul.suldaksuldak.repo.stats.user.UserLiquorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;
    private final UserLiquorRepository userLiquorRepository;
    private final LiquorSearchLogRepository liquorSearchLogRepository;

    /**
     * 유저 - 술 집게 Table에 집계
     */
    public Boolean countSearchCnt(
            Long userId,
            Long liquorId
    ) {
        try {
            Optional<UserLiquorDto> dto = userLiquorRepository.findByUserPriKeyAndLiquorPriKey(userId, liquorId);
            if (dto.isEmpty()) {
                userRepository.findById(userId)
                        .ifPresent(
                                findUser -> {
                                    liquorRepository.findById(liquorId)
                                            .ifPresent(
                                                    findLiquor -> {
                                                        userLiquorRepository.save(
                                                                UserLiquorDto
                                                                        .of(null, userId, liquorId, 0.1)
                                                                        .toEntity(findUser, findLiquor)
                                                        );
                                                    }
                                            );
                                }
                        );
            } else {
                userLiquorRepository.findById(dto.get().getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    userLiquorRepository.save(
                                            UserLiquorDto.addSearchCnt(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND DATA");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 술 검색 집계 추가
     */
    public Boolean createLiquorSearchLog(
            Long liquorPriKey
    ) {
        try {
            if (liquorPriKey == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "Liquor Key is Null");
            String priKey = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
                    + "_" + liquorPriKey;
            liquorRepository.findById(liquorPriKey)
                    .ifPresent(
                            findLiquor -> {
                                liquorSearchLogRepository.save(
                                        LiquorSearchLog.of(
                                                priKey,
                                                findLiquor
                                        )
                                );
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
     * 집계된 술 통계를 기준으로 기간 별 조회
     */
    public Page<Long> getLiquorDataByLogStats(
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    ) {
        try {
            return liquorSearchLogRepository.findLiquorPriKeyByDateRange(
                    pageable,
                    startAt,
                    endAt
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 유저 별 추천 술 목록 조회
     */
    public List<UserLiquorTagDto> getLiquorPriKeyByUserStats(
            Long userPriKey,
            Integer limitNum
    ) {
        try {
            return userLiquorRepository.findRatingByUserId(userPriKey, limitNum);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
