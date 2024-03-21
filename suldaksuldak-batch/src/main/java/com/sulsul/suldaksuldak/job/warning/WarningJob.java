package com.sulsul.suldaksuldak.job.warning;

import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.job.AbstractJob;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Setter
public class WarningJob extends AbstractJob {
    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
        try {
            UserRepository userRepository = ctx.getBean(UserRepository.class);
            LocalDateTime nowTime =
                    LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS);
            LocalDateTime stopStartTime = nowTime.plusDays(3);
            // 받은 경고 접수가 7점이 넘는 유저
            List<User> watchfulUserList = userRepository.findEntityByWarningCount(
                    7.0
            );
            for (User watchfulUser: watchfulUserList) {
                try {
                    log.info(watchfulUser.toString());
                    if (watchfulUser.getSuspensionEndDate() == null) {
                        // 신규 정지
                        watchfulUser.setSuspensionStartDate(stopStartTime);
                        watchfulUser.setSuspensionEndDate(stopStartTime.plusDays(7));
                        log.info("[{}] {} 유저 {} ~ {} 정지 ({}점)",
                                watchfulUser.getId(),
                                watchfulUser.getNickname(),
                                watchfulUser.getSuspensionStartDate(),
                                watchfulUser.getSuspensionEndDate(),
                                watchfulUser.getWarningCnt()
                        );
                        userRepository.save(watchfulUser);
                        continue;
                    }
                    if (LocalDateTime.now().isBefore(watchfulUser.getSuspensionEndDate())) {
                        // 아직 정지 중
                        continue;
                    }
                    Long defDay = ChronoUnit.DAYS.between(
                            watchfulUser.getSuspensionStartDate(),
                            watchfulUser.getSuspensionEndDate()
                    );
                    if (watchfulUser.getWarningCnt() >= 25) {
                        if (defDay <= 30) {
                            // 이 전의 정지 날짜가 30일 정지
                            log.info("영정");
                            watchfulUser.setSuspensionStartDate(stopStartTime);
                            watchfulUser.setSuspensionEndDate(stopStartTime.plusYears(99));
                        }
                    } else if (watchfulUser.getWarningCnt() >= 17) {
                        if (defDay <= 14) {
                            // 이 전의 정지 날짜가 14일 정지였음
                            log.info("30일 정지");
                            watchfulUser.setSuspensionStartDate(stopStartTime);
                            watchfulUser.setSuspensionEndDate(stopStartTime.plusDays(30));
                        }
                    } else if (watchfulUser.getWarningCnt() >= 12) {
                        if (defDay <= 7) {
                            // 이 전의 정지 날짜가 7일 정지였음
                            log.info("14일 정지");
                            watchfulUser.setSuspensionStartDate(stopStartTime);
                            watchfulUser.setSuspensionEndDate(stopStartTime.plusDays(14));
                        }
                    } else {
                        continue;
                    }
                    log.info("[{}] {} 유저 {} ~ {} 정지 ({}점)",
                            watchfulUser.getId(),
                            watchfulUser.getNickname(),
                            watchfulUser.getSuspensionStartDate(),
                            watchfulUser.getSuspensionEndDate(),
                            watchfulUser.getWarningCnt()
                    );
                    userRepository.save(watchfulUser);
                } catch (Exception ignore) {}
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
