package com.sulsul.suldaksuldak.service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.party.PartyDto;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
import com.sulsul.suldaksuldak.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {
    private final FileService fileService;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final PartyTagRepository partyTagRepository;

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
}
