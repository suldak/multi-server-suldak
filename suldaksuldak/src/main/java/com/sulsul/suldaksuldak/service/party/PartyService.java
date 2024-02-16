package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyFeedback;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.domain.user.UserPartyFeedback;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.user.party.UserPartyFeedbackReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.auth.party.UserPartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.feedback.PartyFeedbackRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
import com.sulsul.suldaksuldak.service.common.CheckPriKeyService;
import com.sulsul.suldaksuldak.service.file.FileService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {
    private final CheckPriKeyService checkPriKeyService;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyTagRepository partyTagRepository;
    private final PartyGuestRepository partyGuestRepository;
    private final PartyFeedbackRepository partyFeedbackRepository;
    private final UserPartyFeedbackRepository userPartyFeedbackRepository;

    /**
     * 모임 생성
     */
    public Boolean createParty(
            PartyDto partyDto,
            MultipartFile file
    ) {
        try {
            if (partyDto.getPersonnel() < 3)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임의 최소 인원은 3명입니다."
                );
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
            Optional<User> user = userRepository.findById(partyDto.getHostUserPriKey());
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "주최자를 찾지 못했습니다."
                );
            FileBase fileBase = fileService.saveFile(file);
            Party party = partyRepository.save(
                    partyDto.toEntity(
                            user.get(),
                            fileBase,
                            partyTag.get()
                    )
            );
            String dateStr = UtilTool.getLocalDateTimeString();
            partyGuestRepository.save(
                    PartyGuest.of(
                            dateStr + "_" + party.getId() + "_" + user.get().getId(),
                            party,
                            user.get(),
                            GuestType.CONFIRM
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
     * 모임 기본키로 모임이 있는지 검사하고, 추가로 모임의 삭제 여부와 완료 여부를 검사합니다.
     */
    public Party checkParty(Long priKey, Boolean checkDetail) {
        if (priKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "기본키가 NULL 입니다."
            );
        Optional<Party> party = partyRepository.findById(priKey);
        if (party.isEmpty())
            throw new GeneralException(
                    ErrorCode.NOT_FOUND,
                    "해당 모임 정보를 찾을 수 없습니다."
            );
        if (checkDetail)
            return checkParty(party.get());
        else
            return party.get();
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
                    User user = checkPriKeyService.checkAndGetUser(feedbackObj.getUserPriKey());
                    if (user.getId().equals(writerUserPriKey)) continue;
                    for (Long feedbackPriKey: feedbackObj.getPartyFeedbackPriKeyList()) {
                        Optional<PartyFeedback> partyFeedback =
                                partyFeedbackRepository.findById(feedbackPriKey);
                        if (partyFeedback.isEmpty()) continue;
                        userPartyFeedbackRepository.save(
                                UserPartyFeedback.of(
                                        null,
                                        user,
                                        partyFeedback.get()
                                )
                        );
                        if (user.getLevel() + partyFeedback.get().getScore() >= 100) {
                            user.setLevel(100.0);
                        } else if (user.getLevel() + partyFeedback.get().getScore() < 0) {
                            user.setLevel(0.0);
                        } else {
                            user.setLevel(user.getLevel() + partyFeedback.get().getScore());
                        }
                        userRepository.save(user);
                    }
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
}
