package com.sulsul.suldaksuldak.config;

import com.sulsul.suldaksuldak.component.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ScheduleStarter {
    @Autowired
    private JobManager jobManager;

    @PostConstruct
    private void start() {
        try {
            jobManager.start();
        } catch (Exception e) {
            System.out.println("*************************************************");
            System.out.println("*************************************************");
            System.out.println("|||||||" + e.getMessage() + "|||||||");
            System.out.println("*************************************************");
            System.out.println("*************************************************");
            e.printStackTrace();
        }
    }
}
