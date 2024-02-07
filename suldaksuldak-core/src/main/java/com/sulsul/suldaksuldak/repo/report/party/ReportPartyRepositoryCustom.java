package com.sulsul.suldaksuldak.repo.report.party;

import com.sulsul.suldaksuldak.dto.report.party.ReportPartyDto;

import java.util.List;

public interface ReportPartyRepositoryCustom {
    List<ReportPartyDto> findByOption(
            Long userPriKey,
            Long partyPriKey,
            Long reportReasonPriKey,
            Boolean complete
    );
}
