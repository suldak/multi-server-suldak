package com.sulsul.suldaksuldak.repo.party.schedule;

import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;

import java.util.List;
import java.util.Optional;

public interface PartyScheduleRepositoryCustom {
    Optional<PartySchedule> findByPartyPriKeyAndBatchType(
            Long partyPriKey,
            PartyBatchType partyBatchType
    );

    List<PartySchedule> findByPartyPriKeyAndIsActive(
            Long partyPriKey
    );
}
