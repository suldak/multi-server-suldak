package com.sulsul.suldaksuldak.repo.report.party.comment;

import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentDto;

import java.util.List;

public interface ReportPartyCommentRepositoryCustom {
    List<ReportPartyCommentDto> findByOptions(
            Long userPriKey,
            Long partyPriKey
    );
}
