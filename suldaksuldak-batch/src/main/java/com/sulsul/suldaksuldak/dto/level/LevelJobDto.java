package com.sulsul.suldaksuldak.dto.level;

import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import lombok.Value;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Value
public class LevelJobDto implements JobDtoInterface {
    Long id;

    String jobName;
    String triggerId;
    String triggerCron;
    LocalDateTime nextRunTime;

    public static LevelJobDto of () {
        return new LevelJobDto(
                null,
                JobIdentity.USER_LEVEL_APPLY_JOB.getName(),
                JobIdentity.USER_LEVEL_APPLY_TRIGGER.getName(),
                "0 0 3 ? * MON *",
                null
        );
    }

    public static LevelJobDto of(
            JobKey jobKey,
            Trigger trigger,
            JobDetail jobDetail
    ) {
        Instant nextFireTimeIns =
                trigger.getNextFireTime() == null ?
                        null : trigger.getNextFireTime().toInstant();
        LocalDateTime nextFireTime = nextFireTimeIns == null ?
                null : LocalDateTime.ofInstant(nextFireTimeIns, ZoneId.systemDefault());
        return new LevelJobDto(
                null,
                trigger.getKey().getName(),
                jobKey.getName(),
                null,
                nextFireTime
        );
    }
}
