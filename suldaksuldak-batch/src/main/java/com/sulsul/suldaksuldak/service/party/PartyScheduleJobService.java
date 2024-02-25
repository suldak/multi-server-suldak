package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.BasicJobKey;
import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.dto.party.PartyJobDto;
import com.sulsul.suldaksuldak.job.party.PartyJob;
import com.sulsul.suldaksuldak.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class PartyScheduleJobService implements JobScheduleService {
    @Autowired
    private static ApplicationContext applicationContext;

    @Autowired
    private static PartyScheduleService partyScheduleService;

    @Autowired
    public PartyScheduleJobService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        PartyJobDto partyDto = (PartyJobDto) jobDto;
        HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put(BasicJobKey.PARTY_PRI_KEY.getKeyStr(), partyDto.getId());
        dataMap.put(BasicJobKey.PARTY_BATCH_TYPE.getKeyStr(), partyDto.getPartyBatchType());

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PartyJob.class);
        factoryBean.setName(partyDto.getJobName());
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setApplicationContextJobDataKey("applicationContext");
        factoryBean.setJobDataAsMap(dataMap);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {
        log.info("[ PARTY BATCH ]");
        try {
            for (JobKey jobKey: scheduler.getJobKeys(null)) {
                scheduler.getTriggersOfJob(jobKey).forEach(trigger -> {
                    log.info(trigger.getKey().getName());
                    if (trigger.getKey().getName().startsWith(JobIdentity.PARTY_BATCH_TRIGGER.getName())) {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            PartyJobDto partyJobDto =
                                    PartyJobDto.of(
                                            jobKey,
                                            trigger,
                                            jobDetail
                                    );
                            log.info(partyJobDto.toString());
                        } catch (SchedulerException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
