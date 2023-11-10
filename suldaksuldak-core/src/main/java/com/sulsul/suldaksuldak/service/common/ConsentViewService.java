package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.admin.ConsentItemType;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.consent.ConsentItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsentViewService {
    private final ConsentItemRepository consentItemRepository;

    public List<ConsentItemDto> getConsentList(
//            ConsentItemDto consentItemDto
            ConsentItemType consentItemType,
            Integer itemSeq
    ) {
        try {
            return consentItemRepository
                    .findConsentList(
                            consentItemType,
                            itemSeq
                    );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
