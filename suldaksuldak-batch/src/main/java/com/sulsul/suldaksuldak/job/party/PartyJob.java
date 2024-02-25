package com.sulsul.suldaksuldak.job.party;

import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.job.AbstractJob;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@Setter
public class PartyJob extends AbstractJob {
    Long id;
    PartyBatchType partyBatchType;

    @Override
    protected void executeJob(JobExecutionContext context) throws JobExecutionException {
        try {
//            CheckPriKeyService checkPriKeyService = getCheckPriKeyService();
            log.info("id >> {}", id);
            log.info("partyBatchType >> {}", partyBatchType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
