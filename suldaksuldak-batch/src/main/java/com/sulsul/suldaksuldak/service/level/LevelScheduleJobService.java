package com.sulsul.suldaksuldak.service.level;

import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.service.JobScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelScheduleJobService implements JobScheduleService {
    private final ApplicationContext applicationContext;

    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        return null;
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {

    }
}
