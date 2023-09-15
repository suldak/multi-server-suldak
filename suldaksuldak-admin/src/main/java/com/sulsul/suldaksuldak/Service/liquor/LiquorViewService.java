package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.recipe.LiquorRecipeRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
    private final LiquorSnackRepository liquorSnackRepository;
    private final LiquorRecipeRepository liquorRecipeRepository;


    /**
     * 술 레시피의 기본키로 조회
     */
    public Optional<LiquorRecipeDto> getLiquorRecipe(
            Long priKey
    ) {
        try {
            if (priKey == null) {
                throw new GeneralException(ErrorCode.NOT_FOUND, "PriKey is Null");
            }
            return liquorRecipeRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술에 해당하는 레시피 조회
     */
    public List<LiquorRecipeDto> getLiquorRecipeList (
            Long liquorId
    ) {
        try {
            if (liquorId == null) {
                throw new GeneralException(ErrorCode.NOT_FOUND, "Liquor PriKey is Null");
            }
            return liquorRecipeRepository.findByLiquorPriKey(liquorId);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술로 추천 안주 조회
     */
//    public List<LiquorSnackDto> getLiquorSnack(
//            Long liquorPriKey
//    ) {
//        try {
//            return liquorSnackRepository.findby
//        } catch (GeneralException e) {
//            throw new GeneralException(e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
//        }
//    }
}
