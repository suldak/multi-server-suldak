package com.sulsul.suldaksuldak.service.reservation;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.reservation.ReservationUserDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.reservation.ReservationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.hasText;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationUserRepository reservationUserRepository;

    public Boolean createUser(
            ReservationUserDto reservationUserDto
    ) {
        try {
            if (!hasText(reservationUserDto.getUserEmail())) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND EMAIL");
            }
            Optional<ReservationUserDto> userDto =
                    reservationUserRepository.findByUserEmail(reservationUserDto.getUserEmail());
            if (userDto.isPresent()) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "이미 등록된 이메일 입니다.");
            }
            reservationUserRepository.save(
                    reservationUserDto.toEntity()
            );
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public List<ReservationUserDto> getAllReservationUserList() {
        try {
            return reservationUserRepository.findAllReservationUserList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Optional<ReservationUserDto> getReservationUserByEmail(
            String userEmail
    ) {
        if (!hasText(userEmail)) {
            throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND EMAIL");
        }
        try {
            return reservationUserRepository.findByUserEmail(userEmail);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
