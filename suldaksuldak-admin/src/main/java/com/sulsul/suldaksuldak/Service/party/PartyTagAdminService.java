package com.sulsul.suldaksuldak.Service.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.party.PartyTag;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyTagAdminService {
    private final PartyTagRepository partyTagRepository;

    /**
     * 모임 태그 생성 및 수정
     */
    public Boolean createPartyTag(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                partyTagRepository.save(
                        PartyTag.of(null, name)
                );
            } else {
                partyTagRepository.findById(id)
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (name != null) findEntity.setName(name);
                                    partyTagRepository.save(
                                            findEntity
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            "해당 모임 태그를 찾지 못했습니다."
                                    );
                                }
                        );
            }
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
     * 모임 태그 삭제
     */
    public Boolean deletePartyTag(Long id) {
        try {
            if (id == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키가 NULL 입니다."
                );
            partyTagRepository.deleteById(id);
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
