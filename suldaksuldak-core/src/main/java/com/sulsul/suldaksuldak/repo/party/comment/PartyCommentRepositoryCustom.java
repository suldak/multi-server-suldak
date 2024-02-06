package com.sulsul.suldaksuldak.repo.party.comment;

import com.sulsul.suldaksuldak.dto.party.comment.PartyCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartyCommentRepositoryCustom {
    Page<PartyCommentDto> findByPartyPriKey(
            Long partyPriKey,
            Pageable pageable
    );
    List<PartyCommentDto> findByCommentDep(
            Long partyPriKey,
            String groupComment,
            Integer commentDep
    );
}
