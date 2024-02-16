package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.service.JobScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyScheduleService implements JobScheduleService {
    @Autowired
    private static ApplicationContext applicationContext;

    @Autowired
    private PartyScheduleService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        return null;
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {

    }
}
