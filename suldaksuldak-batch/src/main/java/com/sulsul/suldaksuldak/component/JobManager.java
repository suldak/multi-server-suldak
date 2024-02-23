package com.sulsul.suldaksuldak.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobManager {
    private SchedulerFactory schedulerFactory;
    public static Scheduler scheduler;

    public void start() {
        try {
            schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();

            scheduler.start();
        } catch (SchedulerException e) {
            log.error("[[ERROR]] :: JobManager.start > {}", e.getMessage());
        }
    }
}
