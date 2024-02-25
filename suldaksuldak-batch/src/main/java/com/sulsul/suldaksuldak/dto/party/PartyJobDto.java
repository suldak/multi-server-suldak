package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.BasicJobKey;
import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import com.sulsul.suldaksuldak.tool.ScheduleTool;
import lombok.Value;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Value
public class PartyJobDto implements JobDtoInterface {
    Long id;
    PartyBatchType partyBatchType;

    String jobName;
    String triggerId;
    String triggerCron;
    LocalDateTime nextRunTime;

    public static PartyJobDto of (
            Party party,
            PartyBatchType partyBatchType
    ) {
        Long id = party.getId();
        // 모임 종료 스케줄
        if (partyBatchType.equals(PartyBatchType.SET_RECRUITMENT_END)) {
            return new PartyJobDto(
                    id,
                    PartyBatchType.SET_RECRUITMENT_END,
                    JobIdentity.PARTY_BATCH_JOB_RECRUITMENT_END.getName() + id,
                    JobIdentity.PARTY_BATCH_TRIGGER_RECRUITMENT_END.getName() + id,
                    // 모임 시작 3시간 전
                    ScheduleTool.generateCronStringByLocalDateTime(
//                            party.getMeetingDay().minusHours(3)
                            party.getMeetingDay().minusMinutes(10)
                    ),
                    null
            );
        }
        // 모임 중 스케줄
        else if (partyBatchType.equals(PartyBatchType.SET_ON_GOING)) {
            return new PartyJobDto(
                    id,
                    PartyBatchType.SET_ON_GOING,
                    JobIdentity.PARTY_BATCH_JOB_ON_GOING.getName() + id,
                    JobIdentity.PARTY_BATCH_TRIGGER_ON_GOING.getName() + id,
                    // 모임 시각
                    ScheduleTool.generateCronStringByLocalDateTime(
                            party.getMeetingDay()
                    ),
                    null
            );
        }
        // 모임 완료 스케줄
        else if (partyBatchType.equals(PartyBatchType.SET_MEETING_COMPLETE)) {
            return new PartyJobDto(
                    id,
                    PartyBatchType.SET_MEETING_COMPLETE,
                    JobIdentity.PARTY_BATCH_JOB_MEETING_COMPLETE.getName() + id,
                    JobIdentity.PARTY_BATCH_TRIGGER_MEETING_COMPLETE.getName() + id,
                    // 모임 시각 1시간 후
                    ScheduleTool.generateCronStringByLocalDateTime(
//                            party.getMeetingDay().plusHours(1)
                            party.getMeetingDay().plusMinutes(10)
                    ),
                    null
            );
        }
        // 모인 인원 완료 스케줄
        else if (partyBatchType.equals(PartyBatchType.SET_GUEST_COMPLETE)) {
            return new PartyJobDto(
                    id,
                    PartyBatchType.SET_GUEST_COMPLETE,
                    JobIdentity.PARTY_BATCH_JOB_GUEST_COMPLETE.getName() + id,
                    JobIdentity.PARTY_BATCH_TRIGGER_GUEST_COMPLETE.getName() + id,
                    // 모임 시각 7일 후
                    ScheduleTool.generateCronStringByLocalDateTime(
//                            party.getMeetingDay().plusDays(7)
                            party.getMeetingDay().plusMinutes(15)
                    ),
                    null
            );
        } else {
            return null;
        }
    }

    public static PartyJobDto of (
            JobKey jobKey,
            Trigger trigger,
            JobDetail jobDetail
    ) {
        return new PartyJobDto(
                jobDetail.getJobDataMap().getLong(BasicJobKey.PARTY_PRI_KEY.getKeyStr()),
                PartyBatchType.findText(jobDetail.getJobDataMap().getString(BasicJobKey.PARTY_BATCH_TYPE.getKeyStr())),
                trigger.getKey().getName(),
                jobKey.getName(),
                null,
                LocalDateTime.ofInstant(((Trigger) trigger)
                        .getNextFireTime().toInstant(), ZoneId.systemDefault())
        );
    }
}
