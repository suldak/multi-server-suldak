package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                            null,
                            null,
                            null,
                            null,
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
                            null,
                            null,
                            null,
                            null,
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

    /**
     * 모임 신청을 취소합니다.
     */
    public Boolean partyCancel(
            Long guestPriKey,
            String priKey
    ) {
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키를 찾지 못했습니다."
                );
            if (guestPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "유저 기본키를 찾지 못했습니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findById(priKey);
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "해당 모임을 찾을 수 없습니다."
                );
            if (!partyGuest.get().getUser().getId().equals(guestPriKey))
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "본인만 모임을 취소할 수 있습니다."
                );
            if (
                    partyGuest.get().getConfirm().equals(GuestType.COMPLETE) ||
                            partyGuest.get().getConfirm().equals(GuestType.COMPLETE_WAIT)
            )
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "이미 종료된 모임 입니다."
                );
            partyGuest.get().setConfirm(GuestType.CANCEL);
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
