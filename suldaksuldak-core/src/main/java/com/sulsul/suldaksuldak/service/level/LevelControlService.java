package com.sulsul.suldaksuldak.service.level;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.PartyComplete;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.admin.feedback.GroupUserFeedbackDto;
import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteDto;
import com.sulsul.suldaksuldak.dto.party.complete.PartyCompleteGroupDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.feedback.UserPartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.complete.PartyCompleteRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelControlService {
    private final CheckPriKeyService checkPriKeyService;
    private final UserRepository userRepository;
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;
    private final PartyCompleteRepository partyCompleteRepository;

    // 모임 참가 및 호스트에 대한 레벨 조장
    public Boolean updateUserLevelFromComplete(
            Long partyPriKey
    ) {
        try {
            List<PartyCompleteDto> partyCompleteDtos =
                    partyCompleteRepository.findByPartyPriKey(partyPriKey);
            for (PartyCompleteDto dto: partyCompleteDtos) {
                try {
                    Optional<PartyCompleteGroupDto> completeCheck =
                            partyCompleteRepository.findUserGroupByUserPriKey(dto.getUserPriKey(), false);
                    if (completeCheck.isPresent()) {
                        if (completeCheck.get().getCompleteCnt() % 5 == 0) {
                            // 모임 참여 5회 달성
                            User user = checkPriKeyService.checkAndGetUser(completeCheck.get().getUserPriKey());
                            Double plusLevel = 0.5 * getLevelWeight(user.getLevel());
                            Double newUserLevel = Math.min(user.getLevel() + plusLevel, 99.9);
                            //레벨 조정
                            log.info("[COMPLETE] {} Level Change: {} > {}", user.getNickname(), user.getLevel(), newUserLevel);
                            user.setLevel(newUserLevel);
                            userRepository.save(user);
                            // 처리된 참여 데이터 수정
                            List<PartyComplete> completeData =
                                    partyCompleteRepository.findUnprocessedByUserPriKey(
                                            user.getId(),
                                            false
                                    );
                            for (PartyComplete entity: completeData) {
                                entity.setIsCompleteProcessed(true);
                                entity.setCompleteProcessedAt(LocalDateTime.now());
                                partyCompleteRepository.save(
                                        entity
                                );
                            }
                        }
                    }
                    if (dto.getIsHost()) {
                        Optional<PartyCompleteGroupDto> hostCheck =
                                partyCompleteRepository.findUserGroupByUserPriKey(dto.getUserPriKey(), true);
                        if (hostCheck.isPresent()) {
                            if (hostCheck.get().getHostCnt() % 2 == 0) {
                                // 모임 호스트 2회 달성
                                User user = checkPriKeyService.checkAndGetUser(hostCheck.get().getUserPriKey());
                                Double plusLevel = 0.4 * getLevelWeight(user.getLevel());
                                Double newUserLevel = Math.min(user.getLevel() + plusLevel, 99.9);
                                //레벨 조정
                                log.info("[HOST] {} Level Change: {} > {}", user.getNickname(), user.getLevel(), newUserLevel);
                                user.setLevel(newUserLevel);
                                userRepository.save(user);
                                // 처리된 참여 데이터 수정
                                List<PartyComplete> hostData =
                                        partyCompleteRepository.findUnprocessedByUserPriKey(
                                                user.getId(),
                                                true
                                        );
                                for (PartyComplete entity: hostData) {
                                    entity.setIsHostProcessed(true);
                                    entity.setHostProcessedAt(LocalDateTime.now());
                                    partyCompleteRepository.save(
                                            entity
                                    );
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    // 모임 피드백 레벨 조정
    public Boolean updateUserLevelFromFeedback() {
        try {
            LocalDateTime searchEndTime = LocalDateTime.now();
            LocalDateTime searchStartTime = searchEndTime.minusDays(7);

            List<GroupUserFeedbackDto> groupUserFeedbackDtos =
                    userPartyFeedbackRepository.findGroupDtoByTargetPriKey(
                            searchStartTime,
                            searchEndTime
                    );
            for (GroupUserFeedbackDto feedbackDto: groupUserFeedbackDtos) {
                try {
                    User targetUser = checkPriKeyService.checkAndGetUser(feedbackDto.getTargetUserPriKey());
                    Double goodCount = (feedbackDto.getGoodFeedbackCnt() * 0.1) * getLevelWeight(targetUser.getLevel());
                    Double badCount = feedbackDto.getBadFeedbackCnt() * 0.1;
                    Double newTargetUserLevel =
                            Math.min(Math.max(targetUser.getLevel() + goodCount - badCount, 0.0), 99.9);
                    log.info("{} Level Change: {} > {}", targetUser.getNickname(), targetUser.getLevel(), newTargetUserLevel);
                    targetUser.setLevel(newTargetUserLevel);
                    userRepository.save(targetUser);
                } catch (Exception ignore) {}
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 레벨 구간 별 증가 점수 가중치 조회
     */
    private Double getLevelWeight(
            Double userLevel
    ) {
        if (0 <= userLevel && userLevel <= 15) {
            return 1.1;
        } else if (15.1 <= userLevel && userLevel <= 30) {
            return 1.0;
        } else if (30.1 <= userLevel && userLevel <= 50) {
            return 0.9;
        } else if (50.1 <= userLevel && userLevel <= 80) {
            return 0.7;
        } else if (80.1 <= userLevel && userLevel <= 99.9) {
            return 0.5;
        }
        return 1.0;
    }

    public Boolean updateUserWarningCnt(
            Long userPriKey,
            Double warningCnt
    ) throws GeneralException {
        try {
            return updateUserWarningCnt(
                    checkPriKeyService.checkAndGetUser(userPriKey),
                    warningCnt
            );
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

    public Boolean updateUserWarningCnt(
            User user,
            Double warningCnt
    ) throws GeneralException {
        try {
            user.setWarningCnt(user.getWarningCnt() + warningCnt);
            log.info("[{}] {} 경고 점수 {}", user.getId(), user.getNickname(), user.getWarningCnt());
            userRepository.save(user);
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
