package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartyScheduleJobService implements JobScheduleService {
    @Override
    public <T extends JobDtoInterface> JobDetail jobDetailMaker(T jobDto) {
        return null;
    }

    @Override
    public void printScheduleList(Scheduler scheduler) {

    }
}
