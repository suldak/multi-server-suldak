package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.repo.report.party.ReportPartyRepository;
import com.sulsul.suldaksuldak.repo.stats.party.PartySearchLogRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyViewService {
    private final ReportPartyRepository reportPartyRepository;
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;
    private final PartySearchLogRepository partySearchLogRepository;

    /**
     * 모임 목록 조회
     */
    public Page<PartyRes> getPartyPageList(
            Long searchUser,
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            List<Long> partyTagPriList,
            List<PartyStateType> partyStateTypes,
            Boolean sortBool,
            Pageable pageable
    ) {
        try {
            List<ReportPartyDto> reportPartyDtos =
                    reportPartyRepository.findByOption(
                            searchUser,
                            null,
                            null,
                            null
                    );

            Page<PartyDto> partyDtos = partyRepository.findByOptional(
                    reportPartyDtos.stream().map(ReportPartyDto::getPartyPriKey).toList(),
                    name,
                    searchStartTime,
                    searchEndTime,
                    personnel,
                    partyType,
                    hostUserPriKey,
                    partyTagPriList,
                    partyStateTypes,
                    sortBool,
                    pageable
            );

            return new PageImpl<>(
                    partyDtos.getContent().stream().map(
                            PartyRes::from
                    ).toList(),
                    partyDtos.getPageable(),
                    partyDtos.getTotalElements()
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
     * 파티 참가원 목록 조회
     */
    public List<PartyGuestDto> getPartyGuestList(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            PartyType partyType,
            List<Long> partyTagPriList,
            Long partyPriKey,
            List<GuestType> confirmList
    ) {
        try {
            return partyGuestRepository.findByOptions(
                    searchStartTime,
                    searchEndTime,
                    partyType,
                    partyTagPriList,
                    partyPriKey,
                    null,
                    confirmList
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
     * 유저가 참가하는 모임 목록 조회
     */
    public List<PartyDto> getPartyByUser(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            PartyType partyType,
            List<Long> partyTagPriList,
            Long userPriKey,
            List<GuestType> confirmList,
            Boolean sortBool
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            List<Long> partyPriKeyList =
                    partyGuestRepository.findByOptions(
                            searchStartTime,
                            searchEndTime,
                            partyType,
                            partyTagPriList,
                            null,
                            userPriKey,
                            confirmList
                    ).stream().map(PartyGuestDto::getPartyPriKey).toList();
            return partyRepository.findByPriKeyAndGuestPriKey(partyPriKeyList, userPriKey, sortBool);
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
     * 유저가 Host인 모임 목록
     */
    public List<PartyDto> getPartyByHostPriKey(
            Long userPriKey,
            Boolean sortBool
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            return partyRepository.findByHostPriKey(userPriKey, sortBool);
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
     * 참여자가 많은 순으로 모임 조회
     */
    public List<PartyDto> getTopGuestPartyList(
            Integer limitNum
    ) {
        try {
            List<PartyDto> partyDtos = new ArrayList<>();
            List<Long> partyPriKeyList =
                    partyGuestRepository.findPartyPriKeyByTopGuestCount(
                            limitNum
                    );
            for (Long partyPriKey: partyPriKeyList) {
                Optional<PartyDto> dto =
                        partyRepository.findByPriKey(
                                partyPriKey
                        );
                dto.ifPresent(partyDtos::add);
            }
            return partyDtos;
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
     * 조회가 많은 순으로 모임 조회
     */
    public List<PartyDto> getTopClickPartyList(
            Integer limitNum
    ) {
        try {
            List<PartyDto> partyDtos = new ArrayList<>();
            List<Long> partyPriKeyList =
                    partySearchLogRepository.findPartyPriKeyByTopSearch(limitNum);
            for (Long partyPriKey: partyPriKeyList) {
                Optional<PartyDto> dto =
                        partyRepository.findByPriKey(
                                partyPriKey
                        );
                dto.ifPresent(partyDtos::add);
            }
            return partyDtos;
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
     * 호스트의 알콜 도수가 높은 순으로 모임 조회
     */
    public List<PartyDto> getTopHostLevelPartyList(
            Integer limitNum
    ) {
        try {
            return partyRepository.findByHostLevel(limitNum);
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

    public List<PartyDto> getUserRecommendPartyList(
            Long userPriKey,
            Integer limitNum
    ) {
        try {
            List<Long> tagPriKey =
                    partyGuestRepository.findTahPriKeyByUserRecommend(
                            userPriKey,
                            limitNum
                    );
            return partyRepository.findByTagPriKeyList(
                    UtilTool.removeDuplicates(tagPriKey),
                    limitNum
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
