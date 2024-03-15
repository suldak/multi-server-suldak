package com.sulsul.suldaksuldak.job.level;

import com.sulsul.suldaksuldak.job.AbstractJob;
import com.sulsul.suldaksuldak.service.level.LevelControlService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@Setter
public class LevelJob extends AbstractJob {

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
        try {
            LevelControlService levelBatchService = ctx.getBean(LevelControlService.class);
            levelBatchService.updateUserLevelFromFeedback();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
