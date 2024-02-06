package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
import com.sulsul.suldaksuldak.service.file.FileService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {
    private final FileService fileService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyTagRepository partyTagRepository;
    private final PartyGuestRepository partyGuestRepository;

    /**
     * 모임 생성
     */
    public Boolean createParty(
            PartyDto partyDto,
            MultipartFile file
    ) {
        try {
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
            partyRepository.save(
                    partyDto.toEntity(
                            user.get(),
                            fileBase,
                            partyTag.get()
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
            partyRepository.save(
                    partyDto.updateEntity(
                            party.get()
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
            FileBase oriFileBase = party.get().getFileBase();
//            if (party.get().getFileBase() != null) {
//                String oriFileName = party.get().getFileBase().getFileNm();
//                fileService.deleteFile(oriFileName);
//            }
            FileBase fileBase = fileService.saveFile(file);
            party.get().setFileBase(fileBase);
            partyRepository.save(
                    party.get()
            );
            if (oriFileBase != null)
                fileService.deleteFile(oriFileBase.getFileNm());
            return true;
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 모임 목록 조회
     */
    public Page<PartyRes> getPartyPageList(
            String name,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            Pageable pageable
    ) {
        try {
            Page<PartyDto> partyDtos = partyRepository.findByOptional(
                    name,
                    searchStartTime,
                    searchEndTime,
                    personnel,
                    partyType,
                    hostUserPriKey,
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
     * 모임 참가 신청
     */
    public Boolean participationParty(
            Long partyPriKey,
            Long userPriKey
    ) {
        try {
            if (partyPriKey == null || userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 누락되었습니다."
                );
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 유저를 찾을 수 없습니다."
                );
            Optional<Party> party = partyRepository.findById(partyPriKey);
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임을 찾을 수 없습니다."
                );

            if (party.get().getUser().getId().equals(user.get().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "자신이 호스트인 모임입니다."
                );

            List<PartyGuestDto> partyGuestDtos =
                    partyGuestRepository.findByOptions(
                            party.get().getId(),
                            user.get().getId(),
                            null
                    );
            if (!partyGuestDtos.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 신청한 모임입니다."
                );

            String dateStr = UtilTool.getLocalDateTimeString();

            partyGuestRepository.save(
                    PartyGuest.of(
                            dateStr + "_" + partyPriKey + "_" + userPriKey,
                            party.get(),
                            user.get(),
                            GuestType.WAIT
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
     * 파티 참가원 목록 조회
     */
    public List<PartyGuestDto> getPartyGuestList(
            Long partyPriKey,
            Long userPriKey,
            GuestType confirm
    ) {
        try {
            return partyGuestRepository.findByOptions(
                    partyPriKey,
                    userPriKey,
                    confirm
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
            Long userPriKey,
            GuestType confirm
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            List<Long> partyPriKeyList =
                partyGuestRepository.findByOptions(
                        null,
                        userPriKey,
                        confirm
                ).stream().map(PartyGuestDto::getPartyPriKey).toList();
            return partyRepository.findByPriKeyList(partyPriKeyList);
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
            Long userPriKey
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            return partyRepository.findByHostPriKey(userPriKey);
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
     * 모임과 유저 기본키로
     * 모임 참가 인원의 상태를 수정합니다.
     */
    public Boolean modifiedPartyGuest (
            Long hostPriKey,
            Long partyPriKey,
            Long guestPriKey,
            GuestType confirm
    ) {
        try {
            if (hostPriKey == null || partyPriKey == null || guestPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾지 못했습니다."
                );
            if (confirm == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "confirm 값이 NULL 입니다."
                );

            List<PartyGuestDto> partyGuestDtos =
                    partyGuestRepository.findByOptions(
                            partyPriKey,
                            guestPriKey,
                            null
                    );

            if (partyGuestDtos.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "모임 신청 정보가 존재하지 않습니다."
                );
            
            PartyGuestDto dto = partyGuestDtos.get(0);
            if (!dto.getHostPriKey().equals(hostPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임의 Host가 아닙니다."
                );
            Optional<PartyGuest> party =
                    partyGuestRepository.findById(dto.getId());
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임 신청 정보가 없습니다."
                );
            party.get().setConfirm(confirm);
            partyGuestRepository.save(party.get());
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
     * 관계 기본기
     * 모임 참가 인원의 상태를 수정합니다.
     */
    public Boolean modifiedPartyGuest (
            Long hostPriKey,
            String priKey,
            GuestType confirm
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾지 못했습니다."
                );
            if (confirm == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "confirm 값이 NULL 입니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findById(priKey);
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임을 찾을 수 없습니다."
                );
            if (!hostPriKey.equals(partyGuest.get().getParty().getUser().getId()))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임의 호스트가 아닙니다."
                );
            partyGuest.get().setConfirm(confirm);
            partyGuestRepository.save(partyGuest.get());
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