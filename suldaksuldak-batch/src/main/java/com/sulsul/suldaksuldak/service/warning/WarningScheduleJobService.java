package com.sulsul.suldaksuldak.service.warning;

import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.dto.warning.WarningJobDto;
import com.sulsul.suldaksuldak.job.warning.WarningJob;
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
public class WarningScheduleJobService implements JobScheduleService {
    private final ApplicationContext applicationContext;

    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        WarningJobDto warningJobDto = (WarningJobDto) jobDto;

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(WarningJob.class);
        factoryBean.setName(warningJobDto.getJobName());
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setApplicationContextJobDataKey("applicationContext");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {
        log.info("[ WARNING BATCH ]");
        try {
            for (JobKey jobKey: scheduler.getJobKeys(null)) {
                scheduler.getTriggersOfJob(jobKey).forEach(trigger -> {
                    if (trigger.getKey().getName().startsWith(JobIdentity.WARNING_COUNT_TRIGGER.getName())) {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            WarningJobDto warningJobDto =
                                    WarningJobDto.of(
                                            jobKey,
                                            trigger,
                                            jobDetail
                                    );
                            log.info(warningJobDto.toString());
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
