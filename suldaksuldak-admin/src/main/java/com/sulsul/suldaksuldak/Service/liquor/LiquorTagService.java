package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.bridge.SnToLi;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.LiquorSnack;
import com.sulsul.suldaksuldak.dto.bridge.SnToLiDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorTagService {
    private final LiquorRepository liquorRepository;
    private final LiquorSnackRepository liquorSnackRepository;
    private final SnToLiRepository snToLiRepository;

    /**
     * 추천 안주와 술 연결
     */
    public Boolean createLiquorToSnack(
            Long liquorPriKey,
            Long liquorSnackPriKey
    ) {
        try {
            Optional<Liquor> liquor = liquorRepository.findById(liquorPriKey);
            if (liquor.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
            Optional<LiquorSnack> liquorSnack = liquorSnackRepository.findById(liquorSnackPriKey);
            if (liquorSnack.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR SNACK DATA");

            Optional<SnToLiDto> snToLiDto =
                    snToLiRepository
                            .findByLiquorPriKeyAndLiquorSnackPriKey(
                                    liquorPriKey,
                                    liquorSnackPriKey
                            );
            if (snToLiDto.isEmpty()) {
                snToLiRepository.save(
                        SnToLi.of(
                                null,
                                liquorSnack.get(),
                                liquor.get()
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

}
