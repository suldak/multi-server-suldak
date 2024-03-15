package com.sulsul.suldaksuldak.service.level;

import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.admin.feedback.GroupUserFeedbackDto;
import com.sulsul.suldaksuldak.repo.admin.feedback.UserPartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelControlService {
    private final CheckPriKeyService checkPriKeyService;
    private final UserRepository userRepository;
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;

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
}
