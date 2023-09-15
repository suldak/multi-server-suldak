package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorTagService {
    private final LiquorRepository liquorRepository;
    private final LiquorAbvRepository liquorAbvRepository;

    /**
     * 도수와 술 연결
     */
    public Boolean abvToLiquor(
            Long liquorPriKey,
            Long liquorAbvPriKey
    ) {
        try {
            Optional<LiquorDto> liquorDto = liquorRepository.findByPriKey(liquorPriKey);
            if (liquorDto.isEmpty()) throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND Liquor DATA");
            Optional<LiquorAbv> liquorAbv = liquorAbvRepository.findById(liquorAbvPriKey);
            if (liquorAbv.isEmpty()) throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND Liquor abv DATA");


        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
