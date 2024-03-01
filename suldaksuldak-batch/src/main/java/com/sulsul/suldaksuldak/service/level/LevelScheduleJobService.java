package com.sulsul.suldaksuldak.service.level;

import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.dto.level.LevelJobDto;
import com.sulsul.suldaksuldak.job.level.LevelJob;
import com.sulsul.suldaksuldak.service.JobScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelScheduleJobService implements JobScheduleService {
    private final ApplicationContext applicationContext;

    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        LevelJobDto levelJobDto = (LevelJobDto) jobDto;

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(LevelJob.class);
        factoryBean.setName(levelJobDto.getJobName());
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setApplicationContextJobDataKey("applicationContext");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {
        log.info("[ LEVEL BATCH ]");
        try {
            for (JobKey jobKey: scheduler.getJobKeys(null)) {
                scheduler.getTriggersOfJob(jobKey).forEach(trigger -> {
                    if (trigger.getKey().getName().startsWith(JobIdentity.USER_LEVEL_APPLY_TRIGGER.getName())) {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            LevelJobDto levelJobDto =
                                    LevelJobDto.of(
                                            jobKey,
                                            trigger,
                                            jobDetail
                                    );
                            log.info(levelJobDto.toString());
                        } catch (SchedulerException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
