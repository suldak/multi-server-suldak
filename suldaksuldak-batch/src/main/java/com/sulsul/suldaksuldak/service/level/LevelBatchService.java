package com.sulsul.suldaksuldak.service.level;

import com.sulsul.suldaksuldak.constant.admin.UserValue;
import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.admin.feedback.GroupUserFeedbackDto;
import com.sulsul.suldaksuldak.repo.admin.feedback.UserPartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelBatchService {
    private final CheckPriKeyService checkPriKeyService;
    private final UserRepository userRepository;
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;

    // 모임 피드백 레벨 조정
    public Boolean updateUserLevelFromFeedback() {
        try {
            List<Long> targetUserPriKeyList =
                    userPartyFeedbackRepository.findAllUserPriKey();
            for (Long targetUserPriKey: targetUserPriKeyList) {
                List<GroupUserFeedbackDto> groupUserFeedbackDtos =
                        userPartyFeedbackRepository.findGroupDtoByTargetPriKey(
                                targetUserPriKey
                        );
                try {
                    Long goodCount = groupUserFeedbackDtos.stream().filter(
                            dto -> dto.getFeedbackType().equals(FeedbackType.GOOD)
                    ).mapToLong(GroupUserFeedbackDto::getCount).sum();

                    Long badCount = groupUserFeedbackDtos.stream().filter(
                            dto -> dto.getFeedbackType().equals(FeedbackType.BAD)
                    ).mapToLong(GroupUserFeedbackDto::getCount).sum();

                    User targetUser = checkPriKeyService.checkAndGetUser(targetUserPriKey);
                    Double newLevel =
                            UserValue.DEFAULT_LEVEL.getValue()
                                    + ((goodCount / 3) * 0.1)
                                    - ((badCount / 2) * 0.1);
                    targetUser.setLevel(newLevel);
                    userRepository.save(
                            targetUser
                    );
                    log.info("{} [{}] >> {}", targetUser.getId(), targetUser.getNickname(), targetUser.getLevel());
                } catch (Exception ignore) {}
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }
}
