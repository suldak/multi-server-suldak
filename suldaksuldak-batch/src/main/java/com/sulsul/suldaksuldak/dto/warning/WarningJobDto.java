package com.sulsul.suldaksuldak.dto.warning;

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
public class WarningJobDto implements JobDtoInterface {
    Long id;

    String jobName;
    String triggerId;
    String triggerCron;
    LocalDateTime nextRunTime;

    public static WarningJobDto of () {
        return new WarningJobDto(
                null,
                JobIdentity.WARNING_COUNT_JOB.getName(),
                JobIdentity.WARNING_COUNT_TRIGGER.getName(),
                " 0 0 0 * * *",
                null
        );
    }

    public static WarningJobDto of (
            JobKey jobKey,
            Trigger trigger,
            JobDetail jobDetail
    ) {
        Instant nextFireTimeIns =
                trigger.getNextFireTime() == null ?
                        null : trigger.getNextFireTime().toInstant();
        LocalDateTime nextFireTime = nextFireTimeIns == null ?
                null : LocalDateTime.ofInstant(nextFireTimeIns, ZoneId.systemDefault());
        return new WarningJobDto(
                null,
                trigger.getKey().getName(),
                jobKey.getName(),
                null,
                nextFireTime
        );
    }
}
