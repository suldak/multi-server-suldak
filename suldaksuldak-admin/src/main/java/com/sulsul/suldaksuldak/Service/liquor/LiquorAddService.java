package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.recipe.LiquorRecipeRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorAddService {
    private final LiquorRepository liquorRepository;
    private final LiquorRecipeRepository liquorRecipeRepository;
    private final LiquorSnackRepository liquorSnackRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final SnToLiRepository snToLiRepository;

    /**
     * 술 생성 및 수정
     */
    public Boolean createLiquor(
            LiquorDto liquorDto
    ) {
        try {
            LiquorAbv liquorAbv;
            if (liquorDto.getLiquorAbvId() != null) {
                liquorAbv = liquorAbvRepository.findById(liquorDto.getLiquorAbvId())
                        .orElse(null);
            } else {
                liquorAbv = null;
            }

            if (liquorDto.getId() == null) {
                liquorRepository.save(liquorDto.toEntity(liquorAbv));
            } else {
                liquorRepository.findById(liquorDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorRepository.save(liquorDto.updateEntity(findEntity, liquorAbv));
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND Liquor DATA");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 술의 레피시 저장
     */
    public Boolean createLiquorRecipe(
            LiquorRecipeDto liquorRecipeDto
    ) {
        try {
            if (liquorRecipeDto.getLiquorId() == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND Liquor PRI KEY");
            }
            Optional<Liquor> liquorOptional = liquorRepository.findById(liquorRecipeDto.getLiquorId());
            if (liquorOptional.isEmpty()) {
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND Liquor DATA");
            }
            if (liquorRecipeDto.getId() == null) {
                liquorRecipeRepository.save(liquorRecipeDto.toEntity(liquorOptional.get()));
            } else {
                liquorRecipeRepository.findById(liquorRecipeDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorRecipeRepository.save(
                                            liquorRecipeDto.updateEntity(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND RECIPE DATA");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 추천 안주 생성 및 수정
     */
    public Boolean createLiquorSnack(
            LiquorSnackDto liquorSnackDto
    ) {
        try {
            if (liquorSnackDto.getId() == null) {
                liquorSnackRepository.save(liquorSnackDto.toEntity());
            } else {
                liquorSnackRepository.findById(liquorSnackDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorSnackRepository.save(liquorSnackDto.updateEntity(findEntity));
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND Liquor DATA");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 추천 안주와 술 연결
     */
//    public Boolean create

}
