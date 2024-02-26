package com.sulsul.suldaksuldak.repo.report.party.comment;

import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentDto;

import java.util.List;
import java.util.Optional;

public interface ReportPartyCommentRepositoryCustom {
    List<ReportPartyCommentDto> findByOptions(
            Long userPriKey,
            Long partyPriKey
    );

    Optional<ReportPartyCommentDto> findByCommentPriKey(
            Long userPriKey,
            String commentPriKey
    );
}
