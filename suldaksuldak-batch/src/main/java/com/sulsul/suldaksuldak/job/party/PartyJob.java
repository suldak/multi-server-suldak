package com.sulsul.suldaksuldak.job.party;

import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.dto.party.PartyJobDto;
import com.sulsul.suldaksuldak.job.AbstractJob;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleJobService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
@Setter
public class PartyJob extends AbstractJob {
    Long id;
    PartyBatchType partyBatchType;

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
        try {
            CheckPriKeyService checkPriKeyService = getCheckPriKeyService();
            PartyCommonService partyCommonService = ctx.getBean(PartyCommonService.class);
            PartyScheduleJobService partyScheduleJobService = ctx.getBean(PartyScheduleJobService.class);
            PartyScheduleService partyScheduleService = ctx.getBean(PartyScheduleService.class);
            log.info("id >> {}", id);
            log.info("partyBatchType >> {}", partyBatchType);
            Party party = checkPriKeyService.checkAndGetParty(id);
            if (
                partyBatchType.equals(PartyBatchType.SET_RECRUITMENT_END) ||
                partyBatchType.equals(PartyBatchType.SET_ON_GOING) ||
                partyBatchType.equals(PartyBatchType.SET_MEETING_COMPLETE) ||
                partyBatchType.equals(PartyBatchType.SET_GUEST_COMPLETE)
            ) {
                Boolean result = partyCommonService.partyTotalHandler(party);
                partyScheduleService.deletePartySchedule(
                        party.getId(),
                        partyBatchType
                );
                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                if (!result) {
                    // 나머지 스케줄 취소
                    partyScheduleJobService.deleteJob(
                            scheduler,
                            partyScheduleService.deletePartySchedule(
                                    party.getId(),
                                    PartyJobDto.of(
                                            party,
                                            PartyBatchType.SET_ON_GOING
                                    )
                            )
                    );
                    partyScheduleJobService.deleteJob(
                            scheduler,
                            partyScheduleService.deletePartySchedule(
                                    party.getId(),
                                    PartyJobDto.of(
                                            party,
                                            PartyBatchType.SET_MEETING_COMPLETE
                                    )
                            )
                    );
                    partyScheduleJobService.deleteJob(
                            scheduler,
                            partyScheduleService.deletePartySchedule(
                                    party.getId(),
                                    PartyJobDto.of(
                                            party,
                                            PartyBatchType.SET_GUEST_COMPLETE
                                    )
                            )
                    );
                }
                partyScheduleJobService.printScheduleList(scheduler);
            } else {
                log.error("이게 뭐지?");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
