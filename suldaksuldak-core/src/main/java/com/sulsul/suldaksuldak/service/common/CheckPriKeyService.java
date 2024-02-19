package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.party.Party;
import com.sulsul.suldaksuldak.domain.party.PartyGuest;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.party.PartyRepository;
import com.sulsul.suldaksuldak.repo.party.guest.PartyGuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckPriKeyService {
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;
    private final PartyRepository partyRepository;
    private final PartyGuestRepository partyGuestRepository;

    public User checkAndGetUser(
            Long userPriKey
    ) {
        try {
            if (userPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        ErrorMessage.NOT_FOUND_USER_PRI_KEY
                );
            Optional<User> user =
                    userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        ErrorMessage.NOT_FOUND_USER
                );
            return user.get();
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

    public Party checkAndGetParty(
            Long partyPriKey
    ) {
        try {
            if (partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 기본키가 누락되었습니다."
                );
            Optional<Party> party = partyRepository.findById(partyPriKey);
            if (party.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임을 찾을 수 없습니다."
                );
            return party.get();
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

    public PartyGuest checkAndGetPartyGuest(
            String partyGuestPriKey
    ) {
        try {
            if (partyGuestPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "모임 인원 기본키가 NULL 입니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findById(partyGuestPriKey);
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임 인월 정보를 찾을 수 없습니다."
                );
            return partyGuest.get();
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

    public PartyGuest checkAndGetPartyGuest(
            Long userPriKey,
            Long partyPriKey
    ) {
        try {
            if (userPriKey == null || partyPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "조회될 기본키가 NULL 입니다."
                );
            Optional<PartyGuest> partyGuest =
                    partyGuestRepository.findByUserPriKeyAndPartyPriKey(
                            userPriKey,
                            partyPriKey
                    );
            if (partyGuest.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 모임 인원 정보를 찾을 수 없습니다."
                );
            return partyGuest.get();
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

    public Liquor checkAndGetLiquor(
            Long liquorPriKey
    ) {
        try {
            if (liquorPriKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "술 기본키가 NULL 입니다."
                );
            Optional<Liquor> liquor = liquorRepository.findById(liquorPriKey);
            if (liquor.isEmpty())
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        "해당 술 정보를 찾을 수 없습니다."
                );
            return liquor.get();
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
