package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyJobDto;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import com.sulsul.suldaksuldak.service.level.LevelControlService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleJobService;
import com.sulsul.suldaksuldak.service.party.PartyScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/batch/party")
@Api(tags = "[BATCH] 모임 데이터 스케줄 관리")
public class PartyScheduleJobController {
    private final PartyScheduleService partyScheduleService;
    private final CheckPriKeyService checkPriKeyService;
    private final PartyScheduleJobService partyScheduleJobService;
    private final LevelControlService levelControlService;
    private final PartyCommonService partyCommonService;

    @ApiOperation(
            value = "1. 모임 생성 시 스케줄 생성",
            notes = "모임이 새로 생성되면, 모임 시간 3시간 전에 모집 종료로 수정하는 스케줄 등록"
    )
    @PostMapping("/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> createPartySchedule(
            @PathVariable Long partyPriKey
    ) {
        log.info("PostMapping >> {}", partyPriKey);
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            // 모집 종료 스케줄
            partyScheduleJobService.addSchedule(
                    scheduler,
                    partyScheduleService.createPartySchedule(
                            party,
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_RECRUITMENT_END
                            )
                    )
            );
            // 모임 중 스케줄
            partyScheduleJobService.addSchedule(
                    scheduler,
                    partyScheduleService.createPartySchedule(
                            party,
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_ON_GOING
                            )
                    )
            );
            // 모임 완료 스케줄
            partyScheduleJobService.addSchedule(
                    scheduler,
                    partyScheduleService.createPartySchedule(
                            party,
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_MEETING_COMPLETE
                            )
                    )
            );
            // 모임 인원 완료 스케줄
            partyScheduleJobService.addSchedule(
                    scheduler,
                    partyScheduleService.createPartySchedule(
                            party,
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_GUEST_COMPLETE
                            )
                    )
            );
            partyScheduleJobService.printScheduleList(scheduler);
            return ApiDataResponse.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiDataResponse.of(false);
        }
    }

    @ApiOperation(
            value = "2. 모임 수정 시 스케줄 재 등록",
            notes = "모임이 수정되면, 수정 사항에 맞추어 스케줄 재 등록"
    )
    @PutMapping("/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> modifiedPartySchedule(
            @PathVariable Long partyPriKey
    ) {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            // 모집 종료 스케줄
            partyScheduleJobService.updateJob(
                    scheduler,
                    partyScheduleService.modifiedPartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_RECRUITMENT_END
                            )
                    )
            );
            // 모임 중 스케줄
            partyScheduleJobService.updateJob(
                    scheduler,
                    partyScheduleService.modifiedPartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_ON_GOING
                            )
                    )
            );
            // 모임 완료 스케줄
            partyScheduleJobService.updateJob(
                    scheduler,
                    partyScheduleService.modifiedPartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_MEETING_COMPLETE
                            )
                    )
            );
            // 모임 인원 완료 스케줄
            partyScheduleJobService.updateJob(
                    scheduler,
                    partyScheduleService.modifiedPartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_GUEST_COMPLETE
                            )
                    )
            );
            partyScheduleJobService.printScheduleList(scheduler);
            return ApiDataResponse.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiDataResponse.of(false);
        }
    }

    @ApiOperation(
            value = "3. 모임 삭제 및 취소 시 모든 스케줄 취소",
            notes = "모임이 삭제 및 취소 할 때 등록되어있던 모든 스케줄을 취소합니다."
    )
    @DeleteMapping("/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> deletePartySchedule(
            @PathVariable Long partyPriKey
    ) {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            // 모집 종료 스케줄
            partyScheduleJobService.deleteJob(
                    scheduler,
                    partyScheduleService.deletePartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_RECRUITMENT_END
                            )
                    )
            );
            // 모임 중 스케줄
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
            // 모임 완료 스케줄
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
            // 모임 인원 완료 스케줄
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
            partyScheduleJobService.printScheduleList(scheduler);
            return ApiDataResponse.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiDataResponse.of(false);
        }
    }

    @ApiOperation(
            value = "4. 모임 모집 완료 처리",
            notes = "호스트가 모임의 모집을 종료하면, 모집 종료 스케줄 취소"
    )
    @PutMapping("/recruitment-end/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> partyScheduleSetRecruitmentEnd(
            @PathVariable Long partyPriKey
    ) {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            // 모집 종료 스케줄
            partyScheduleJobService.deleteJob(
                    scheduler,
                    partyScheduleService.deletePartySchedule(
                            party.getId(),
                            PartyJobDto.of(
                                    party,
                                    PartyBatchType.SET_RECRUITMENT_END
                            )
                    )
            );
            partyScheduleJobService.printScheduleList(scheduler);
            return ApiDataResponse.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiDataResponse.of(false);
        }
    }

    @GetMapping("/test-party/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> checkParty(
            @PathVariable Long partyPriKey
    ) {
        Party party =
                checkPriKeyService.checkAndGetParty(partyPriKey);
        return ApiDataResponse.of(
                partyCommonService.setPartyComplete(party)
        );
    }

    @GetMapping("/test/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> check(
            @PathVariable Long partyPriKey
    ) {
        return ApiDataResponse.of(
                levelControlService.updateUserLevelFromComplete(partyPriKey)
        );
    }
}
