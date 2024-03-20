package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.component.StartUpEventListener;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyBatchType;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.batch.PartySchedule;
import com.sulsul.suldaksuldak.dto.party.PartyJobDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.schedule.PartyScheduleRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.common.PartyCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyScheduleService {
    private final CheckPriKeyService checkPriKeyService;
    private final StartUpEventListener startUpEventListener;
    private final PartyCommonService partyCommonService;
    private final PartyScheduleRepository partyScheduleRepository;

    /**
     * 모임 스케줄 데이터 생성
     */
    public PartyJobDto createPartySchedule(
            Party party,
            PartyJobDto partyJobDto
    ) {
        try {
            partyScheduleRepository.save(
                    PartySchedule.of(
                            null,
                            party,
                            partyJobDto.getTriggerCron(),
                            true,
                            partyJobDto.getPartyBatchType(),
                            startUpEventListener.getMyIpAddress()
                    )
            );
            return partyJobDto;
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

    public PartyJobDto modifiedPartySchedule(
            Long partyPriKey,
            PartyJobDto partyJobDto
    ) {
        try {
            if (partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            Optional<PartySchedule> partySchedule =
                    partyScheduleRepository.findByPartyPriKeyAndBatchType(
                            partyPriKey,
                            partyJobDto.getPartyBatchType()
                    );
            if (partySchedule.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임 스케줄 정보를 찾을 수 없습니다."
                );
            partySchedule.get().setCronStr(partyJobDto.getTriggerCron());
            partyScheduleRepository.save(
                    partySchedule.get()
            );
            return partyJobDto;
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

    public PartyJobDto deletePartySchedule(
            Long partyPriKey,
            PartyJobDto partyJobDto
    ) {
        try {
            deletePartySchedule(
                    partyPriKey,
                    partyJobDto.getPartyBatchType()
            );
            return partyJobDto;
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

    public Boolean deletePartySchedule(
            Long partyPriKey,
            PartyBatchType partyBatchType
    ) {
        try {
            if (partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            Optional<PartySchedule> partySchedule =
                    partyScheduleRepository.findByPartyPriKeyAndBatchType(
                            partyPriKey,
                            partyBatchType
                    );
            if (partySchedule.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임 스케줄 정보를 찾을 수 없습니다."
                );
            partySchedule.get().setIsActive(false);
            partyScheduleRepository.save(
                    partySchedule.get()
            );
            return true;
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

    public List<PartySchedule> getPartyScheduleList(
            Long partyPriKey
    ) {
        try {
            return partyScheduleRepository.findByPartyPriKeyAndIsActive(
//                    startUpEventListener.getMyIpAddress(),
                    null,
                    partyPriKey
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

    /**
     * 시간이 지난 모임 스케줄을 처리 합니다.
     */
    public Boolean handleSchedules(
            List<PartySchedule> missingList
    ) {
        try {
            for (PartySchedule partySchedule: missingList) {
                try {
                    if (
                            partySchedule.getPartyBatchType().equals(PartyBatchType.SET_RECRUITMENT_END) ||
                            partySchedule.getPartyBatchType().equals(PartyBatchType.SET_ON_GOING) ||
                            partySchedule.getPartyBatchType().equals(PartyBatchType.SET_MEETING_COMPLETE) ||
                            partySchedule.getPartyBatchType().equals(PartyBatchType.SET_GUEST_COMPLETE)
                    ) {
                        Party party =
                                checkPriKeyService.checkAndGetParty(partySchedule.getParty().getId());
                        partyCommonService.partyTotalHandler(party);
                    }
                    partySchedule.setIsActive(false);
                    partyScheduleRepository.save(partySchedule);
                } catch (Exception ignore) {}
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }
}
