package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelReasonDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.cancel.reason.PartyCancelReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyCancelReasonViewService {
    private final PartyCancelReasonRepository partyCancelReasonRepository;

    public List<PartyCancelReasonDto> getPartyCancelReasonList(
            PartyRoleType partyRoleType
    ) {
        try {
            return partyCancelReasonRepository.findByPartyRoleType(
                    partyRoleType
            );
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }
}
