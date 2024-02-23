package com.sulsul.suldaksuldak.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class BasicJobListener implements JobListener {
    private Long startTime;

    @Override
    public String getName() {
        return BasicJobListener.class.getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        try {
            this.startTime = System.currentTimeMillis();
            log.info(String.format("[%s] 작업시작", context.getJobDetail().getKey().toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        try {
            log.info(String.format("[%s] 작업중단_%.2f 소요",
                    context.getJobDetail().getKey().toString(),
                    (System.currentTimeMillis() - this.startTime) / 1000.0)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            log.info(String.format("[%s] 작업완료_%.2f 소요",
                    context.getJobDetail().getKey().toString(),
                    (System.currentTimeMillis() - this.startTime) / 1000.0)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
