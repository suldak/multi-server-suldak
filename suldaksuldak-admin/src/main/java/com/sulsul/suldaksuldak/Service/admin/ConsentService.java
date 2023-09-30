package com.sulsul.suldaksuldak.Service.admin;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.consent.ConsentItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsentService {
    private final ConsentItemRepository consentItemRepository;

    public Boolean createConsentItem(
            ConsentItemDto consentItemDto
    ) {
        try {
            if (consentItemDto.getId() == null) {
                consentItemRepository.save(consentItemDto.toEntity());
            } else {
                consentItemRepository.findById(consentItemDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    consentItemRepository.save(consentItemDto.updateEntity(findEntity));
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND DATA");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    public Boolean deleteConsentItem(
            Long id
    ) {
        try {
            if (id == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "ID IS NULL");
            }
            consentItemRepository.deleteById(id);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
