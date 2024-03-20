package com.sulsul.suldaksuldak.component;

import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import com.sulsul.suldaksuldak.dto.level.LevelJobDto;
import com.sulsul.suldaksuldak.dto.party.PartyJobDto;
import com.sulsul.suldaksuldak.service.level.LevelScheduleJobService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleJobService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleService;
import com.sulsul.suldaksuldak.tool.ScheduleTool;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class JobManager {
    private SchedulerFactory schedulerFactory;
    public static Scheduler scheduler;

    @Autowired
    private PartyScheduleJobService partyScheduleJobService;
    @Autowired
    private PartyScheduleService partyScheduleService;
    @Autowired
    private LevelScheduleJobService levelScheduleJobService;

    public void start() {
        try {
            schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();

            scheduler.start();
        } catch (SchedulerException e) {
            log.error("[[ERROR]] :: JobManager.start > {}", e.getMessage());
        }

        List<PartySchedule> partySchedules =
                partyScheduleService.getPartyScheduleList(null);

        List<PartySchedule> missingPartySchedule = new ArrayList<>();
        for (PartySchedule partySchedule: partySchedules) {
            LocalDateTime fireTime =
                    ScheduleTool.cronToLocalDateTime(partySchedule.getCronStr());
            if (LocalDateTime.now().isBefore(fireTime)) {
                partyScheduleJobService.addSchedule(
                        scheduler,
                        PartyJobDto.of(
                                partySchedule.getParty(),
                                partySchedule.getPartyBatchType(),
                                partySchedule.getCronStr()
                        )
                );
            } else {
                missingPartySchedule.add(partySchedule);
            }
        }
        List<PartySchedule> sortMissingList =
                missingPartySchedule.stream()
                        .sorted(Comparator.comparing((PartySchedule ps) -> ps.getParty().getId())
                                .thenComparing((PartySchedule ps) -> ps.getPartyBatchType().getNum()))
                        .toList();
        partyScheduleService.handleSchedules(sortMissingList);

        levelScheduleJobService.addSchedule(
                scheduler,
                LevelJobDto.of()
        );
        partyScheduleJobService.printScheduleList(scheduler);
        levelScheduleJobService.printScheduleList(scheduler);
    }
}
