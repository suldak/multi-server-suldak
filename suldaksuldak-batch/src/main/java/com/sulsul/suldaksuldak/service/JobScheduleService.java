package com.sulsul.suldaksuldak.service;

import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.job.BasicJobListener;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;

public interface JobScheduleService {
    <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto);

    default <T extends JobDtoInterface> Boolean registerJob(
            Scheduler scheduler,
            T scheduleDto
    ) {
        boolean result = false;
        try {
            result = setJobSchedule(
                    scheduler,
                    jobDetailMaker(scheduleDto),
                    scheduleDto
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    default <T extends JobDtoInterface> boolean setJobSchedule (
            Scheduler scheduler,
            JobDetail jobDetail,
            T jobDto
    ) {
        try {
            boolean overlap = false;
            for (JobKey jobKey: scheduler.getJobKeys(null)) {
                if (StringUtils.equalsIgnoreCase(jobKey.getName(), jobDto.getJobName())) {
                    overlap = true;
                }
            }
            if (overlap) {
                return updateJob(scheduler, jobDto);
            } else {
                Trigger trigger =
                        TriggerBuilder.newTrigger()
                                .withIdentity(
                                        jobDto.getTriggerId()
                                )
                                .withSchedule(CronScheduleBuilder.cronSchedule(jobDto.getTriggerCron()))
                                .build();

                scheduler
                        .getListenerManager()
                        .addJobListener(
                                new BasicJobListener(),
                                KeyMatcher.keyEquals(jobDetail.getKey())
                        );

                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    default <T extends JobDtoInterface> Boolean updateJob (
            Scheduler scheduler,
            T jobDto
    ) {
        try {
            JobDetail jobDetail = jobDetailMaker(jobDto);
            Trigger trigger =
                    TriggerBuilder.newTrigger()
                            .withIdentity(
                                    jobDto.getTriggerId()
                            )
                            .withSchedule(CronScheduleBuilder.cronSchedule(jobDto.getTriggerCron()))
                            .build();
            if (deleteJob(scheduler, jobDto)) {
                scheduler
                        .getListenerManager()
                        .addJobListener(
                                new BasicJobListener(),
                                KeyMatcher.keyEquals(jobDetail.getKey())
                        );
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    default <T extends JobDtoInterface> Boolean addSchedule(
            Scheduler scheduler,
            T jobDto
    ) {
        try {
            return setJobSchedule(
                    scheduler,
                    jobDetailMaker(jobDto),
                    jobDto
            );
        } catch (Exception e) {
            return false;
        }
    }

    default <T extends JobDtoInterface> Boolean deleteJob(
            Scheduler scheduler,
            T jobDto
    ) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobDto.getJobName()));
            scheduler.unscheduleJob(new TriggerKey(jobDto.getTriggerId()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void printScheduleList(
            Scheduler scheduler
    );
}
