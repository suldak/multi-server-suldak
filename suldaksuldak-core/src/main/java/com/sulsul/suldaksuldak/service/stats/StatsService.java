package com.sulsul.suldaksuldak.service.stats;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.stats.user.UserLiquorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;
    private final UserLiquorRepository userLiquorRepository;

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
}
