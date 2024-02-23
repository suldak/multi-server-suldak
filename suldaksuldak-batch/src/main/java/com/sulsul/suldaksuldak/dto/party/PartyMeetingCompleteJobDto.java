package com.sulsul.suldaksuldak.dto.party;

import com.sulsul.suldaksuldak.constant.BasicJobKey;
import com.sulsul.suldaksuldak.constant.JobIdentity;
import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import com.sulsul.suldaksuldak.dto.JobDtoInterface;
import lombok.Value;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Value
public class PartyMeetingCompleteJobDto implements JobDtoInterface {
    Long id;
    PartyBatchType partyBatchType;

    String triggerId;
    String jobName;
    String triggerCron;
    LocalDateTime nextRunTime;
    public static PartyRecruitmentEndJobDto of (
            Long id,
            String triggerCron
    ) {
        return new PartyRecruitmentEndJobDto(
                id,
                PartyBatchType.SET_MEETING_COMPLETE,
                JobIdentity.PARTY_BATCH_JOB_MEETING_COMPLETE.getName() + id,
                JobIdentity.PARTY_BATCH_TRIGGER_MEETING_COMPLETE.getName() + id,
                triggerCron,
                null
        );
    }

    public static PartyRecruitmentEndJobDto of (
            PartySchedule partySchedule
    ) {
        return new PartyRecruitmentEndJobDto(
                partySchedule.getParty().getId(),
                PartyBatchType.SET_MEETING_COMPLETE,
                JobIdentity.PARTY_BATCH_JOB_MEETING_COMPLETE.getName() + partySchedule.getParty().getId(),
                JobIdentity.PARTY_BATCH_TRIGGER_MEETING_COMPLETE.getName() + partySchedule.getParty().getId(),
                partySchedule.getCronStr(),
                null
        );
    }

    public static PartyRecruitmentEndJobDto of (
            JobKey jobKey,
            Trigger trigger,
            JobDetail jobDetail
    ) {
        return new PartyRecruitmentEndJobDto(
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
