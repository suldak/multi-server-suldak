package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.recipe.LiquorRecipeRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.tag.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.tag.name.LiquorNameRepository;
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
    private final LiquorDetailRepository liquorDetailRepository;
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorNameRepository liquorNameRepository;

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
            LiquorDetail liquorDetail;
            if (liquorDto.getLiquorDetailId() != null) {
                liquorDetail = liquorDetailRepository.findById(liquorDto.getLiquorDetailId())
                        .orElse(null);
            } else {
                liquorDetail = null;
            }
            DrinkingCapacity drinkingCapacity;
            if (liquorDto.getDrinkingCapacityId() != null) {
                drinkingCapacity = drinkingCapacityRepository.findById(liquorDto.getDrinkingCapacityId())
                        .orElse(null);
            } else {
                drinkingCapacity = null;
            }
            LiquorName liquorName;
            if (liquorDto.getLiquorNameId() != null) {
                liquorName = liquorNameRepository.findById(liquorDto.getLiquorNameId()).orElse(null);
            } else {
                liquorName = null;
            }

            if (liquorDto.getId() == null) {
                liquorRepository.save(liquorDto.toEntity(liquorAbv, liquorDetail, drinkingCapacity, liquorName));
            } else {
                liquorRepository.findById(liquorDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorRepository.save(
                                            liquorDto.updateEntity(findEntity, liquorAbv, liquorDetail, drinkingCapacity, liquorName)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            ErrorMessage.NOT_FOUND_LIQUOR_DATA
                                    );
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
                throw new GeneralException(
                        ErrorCode.NOT_FOUND,
                        ErrorMessage.NOT_FOUND_LIQUOR_DATA
                );
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
            e.printStackTrace();
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
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            ErrorMessage.NOT_FOUND_LIQUOR_DATA
                                    );
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
}
