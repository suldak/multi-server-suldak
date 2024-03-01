package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.admin.feedback.UserPartyFeedback;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackReq;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.admin.feedback.UserPartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.file.FileService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {
    private final CheckPriKeyService checkPriKeyService;
    private final FileService fileService;
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;
    private final PartyRepository partyRepository;
    private final PartyTagRepository partyTagRepository;
    private final PartyGuestRepository partyGuestRepository;

    /**
     * 모임 생성
     */
    public Long createParty(
            PartyDto partyDto,
            MultipartFile file
    ) {
        try {
            if (partyDto.getName() == null || partyDto.getName().isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 이름은 필수 입력 사항입니다."
                );
            if (partyDto.getPersonnel() < 3)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 최소 인원은 3명입니다."
                );
            checkPartyData(partyDto);
            if (partyDto.getHostUserPriKey() == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "주최자 기본키가 없습니다."
                );
            if (partyDto.getTagPriKey() == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 태그 기본키가 누락되었습니다."
                );
            Optional<PartyTag> partyTag = partyTagRepository.findById(partyDto.getTagPriKey());
            if (partyTag.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임 태그를 찾을 수 없습니다."
                );
            User user = checkPriKeyService.checkAndGetUser(partyDto.getHostUserPriKey());
            FileBase fileBase = fileService.saveFile(file);
            Party party = partyRepository.save(
                    partyDto.toEntity(
                            user,
                            fileBase,
                            partyTag.get()
                    )
            );
            String dateStr = UtilTool.getLocalDateTimeString();
            partyGuestRepository.save(
                    PartyGuest.of(
                            dateStr + "_" + party.getId() + "_" + user.getId(),
                            party,
                            user,
                            GuestType.CONFIRM
                    )
            );
            return party.getId();
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
     * 모임 수정
     */
    public Boolean modifiedParty(
            Long priKey,
            PartyDto partyDto
    ) {
        try {
            Party party = checkParty(priKey, true);
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            checkPartyData(partyDto);
            partyRepository.save(
                    partyDto.updateEntity(
                            party
                    )
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

    /**
     * 모임의 사진 변경
     */
    public Boolean modifiedPartyPicture(
            Long priKey,
            MultipartFile file
    ) {
        try {
            Party party = checkParty(priKey, true);
            if (party.getPartyStateType().equals(PartyStateType.RECRUITMENT_END))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모집이 종료된 모임은 수정할 수 없습니다."
                );
            FileBase oriFileBase = party.getFileBase();
            FileBase fileBase = fileService.saveFile(file);
            party.setFileBase(fileBase);
            partyRepository.save(party);
            if (oriFileBase != null)
                fileService.deleteFile(oriFileBase.getFileNm());
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

    /**
     * 모임 기본키로 모임 조회
     */
    public Optional<PartyDto> getPartyDto(
            Long priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "유효하지 않은 기본키 입니다."
                );
            return partyRepository.findByPriKey(priKey);
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
     * 모임 피드백 저장
     */
    public Boolean createUserPartyFeedback(
            Long writerUserPriKey,
            Long partyPriKey,
            List<UserPartyFeedbackReq.FeedbackObj> feedbackObjs
    ) {
        try {
            Party party = checkPriKeyService.checkAndGetParty(partyPriKey);
            if (!party.getPartyStateType().equals(PartyStateType.MEETING_COMPLETE))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임이 완료된 모임이 아닙니다."
                );
            PartyGuest partyGuest =
                    checkPriKeyService.checkAndGetPartyGuest(
                            writerUserPriKey,
                            partyPriKey
                    );
            if (!partyGuest.getParty().getPartyStateType().equals(PartyStateType.MEETING_COMPLETE))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임이 완료된 모임이 아닙니다."
                );
            if (!partyGuest.getConfirm().equals(GuestType.COMPLETE_WAIT))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "완료 대기상태가 아닙니다."
                );

            for (UserPartyFeedbackReq.FeedbackObj feedbackObj: feedbackObjs) {
                try {
                    // 자기 자신에 대한 피드백 무시
                    if (writerUserPriKey.equals(feedbackObj.getUserPriKey())) continue;
                    User user = checkPriKeyService.checkAndGetUser(feedbackObj.getUserPriKey());
                    userPartyFeedbackRepository.save(
                            UserPartyFeedback.of(
                                    null,
                                    feedbackObj.getFeedbackType(),
                                    feedbackObj.getComment(),
                                    party,
                                    partyGuest.getUser(),
                                    user
                            )
                    );
//                    if (user.getId().equals(writerUserPriKey)) continue;
//                    if (user.getLevel() + feedbackObj.getFeedbackScore() >= 100) {
//                        user.setLevel(100.0);
//                    } else if (user.getLevel() + feedbackObj.getFeedbackScore() < 0) {
//                        user.setLevel(0.0);
//                    } else {
//                        user.setLevel(user.getLevel() + feedbackObj.getFeedbackScore());
//                    }
//                    userRepository.save(user);
                } catch (Exception ignore) {}
            }
            partyGuest.setConfirm(GuestType.COMPLETE);
            partyGuestRepository.save(partyGuest);
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

    /**
     * 모임 기본키로 모임이 있는지 검사하고, 추가로 모임의 삭제 여부와 완료 여부를 검사합니다.
     */
    public Party checkParty(Long priKey, Boolean checkDetail) {
        Party party = checkPriKeyService.checkAndGetParty(priKey);
        if (checkDetail)
            return checkParty(party);
        else
            return party;
    }

    /**
     * 모임의 삭제 여부와 완료 여부를 검사합니다.
     */
    public Party checkParty(Party party) {
        if (party.getPartyStateType().equals(PartyStateType.MEETING_DELETE))
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "이미 삭제된 모임입니다."
            );
        if (party.getPartyStateType().equals(PartyStateType.ON_GOING))
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "모임이 진행 중 입니다."
            );
        if (party.getPartyStateType().equals(PartyStateType.MEETING_COMPLETE))
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "이미 완료된 모임입니다."
            );
        if (party.getPartyStateType().equals(PartyStateType.MEETING_CANCEL))
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "이미 취소된 모임입니다."
            );
        return party;
    }

    private PartyDto checkPartyData(
            PartyDto partyDto
    ) {
        try {
            if (partyDto.getMeetingDay() == null ||
                    !LocalDateTime.now().isBefore(partyDto.getMeetingDay()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 시간이 유효하지 않습니다."
                );
            if (partyDto.getContactType() == null || partyDto.getContactType().isBlank())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "연락 수단을 입력해주세요."
                );
            if (partyDto.getPartyType() == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 타입은 필수 입력 사항입니다."
                );
            if (partyDto.getPartyType().equals(PartyType.ON_LINE)) {
                if (partyDto.getUseProgram() == null || partyDto.getUseProgram().isBlank())
                    throw new GeneralException(
                            ErrorCode.BAD_REQUEST,
                            "온라인 모임에, 사용 프로그램은 필수값 입니다."
                    );
                if (partyDto.getOnlineUrl() == null || partyDto.getOnlineUrl().isBlank())
                    throw new GeneralException(
                            ErrorCode.BAD_REQUEST,
                            "온라인 모임에, URL은 필수값 입니다."
                    );
            } else if (partyDto.getPartyType().equals(PartyType.OFF_LINE)) {
                if (partyDto.getPartyPlace() == null || partyDto.getPartyPlace().isBlank())
                    throw new GeneralException(
                            "오프라인 모임에서, 모임 장소는 필수값 입니다."
                    );
            } else {
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 타입이 유효하지 않습니다. (ON_LINE / OFF_LINE)"
                );
            }
            return partyDto;
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
