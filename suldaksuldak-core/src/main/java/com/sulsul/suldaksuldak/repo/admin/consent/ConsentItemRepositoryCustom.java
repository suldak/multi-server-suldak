package com.sulsul.suldaksuldak.repo.admin.consent;

import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemDto;

import java.util.List;

public interface ConsentItemRepositoryCustom {
    List<ConsentItemDto> findConsentList(
            ConsentItemType consentItemType,
            Integer itemSeq
    );
}
