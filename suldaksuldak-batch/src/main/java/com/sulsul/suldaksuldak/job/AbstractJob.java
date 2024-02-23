package com.sulsul.suldaksuldak.job;

import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Setter
public abstract class AbstractJob extends QuartzJobBean {
    protected ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        ctx = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
        executeJob(context);
    }

    protected CheckPriKeyService getCheckPriKeyService() {
        return ctx.getBean(CheckPriKeyService.class);
    }

    protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException;
}
