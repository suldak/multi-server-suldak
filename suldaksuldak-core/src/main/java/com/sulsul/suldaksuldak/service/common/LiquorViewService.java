package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.recipe.LiquorRecipeRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.tag.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.tag.material.LiquorMaterialRepository;
import com.sulsul.suldaksuldak.repo.tag.name.LiquorNameRepository;
import com.sulsul.suldaksuldak.repo.tag.sell.LiquorSellRepository;
import com.sulsul.suldaksuldak.repo.tag.state.StateTypeRepository;
import com.sulsul.suldaksuldak.repo.tag.taste.TasteTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
    private final LiquorRepository liquorRepository;
    private final LiquorRecipeRepository liquorRecipeRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorNameRepository liquorNameRepository;
    private final LiquorSnackRepository liquorSnackRepository;
    private final LiquorSellRepository liquorSellRepository;
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final StateTypeRepository stateTypeRepository;
    private final TasteTypeRepository tasteTypeRepository;

    /**
     * 술 레시피의 기본키로 조회
     */
    public Optional<LiquorRecipeDto> getLiquorRecipe(
            Long liquorPriKey
    ) {
        try {
            if (liquorPriKey == null) {
                throw new GeneralException(ErrorCode.NOT_FOUND, "PriKey is Null");
            }
            return liquorRecipeRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술에 관련된 모든 데이터 조회
     */
    public LiquorTotalRes getLiquorTotalData(
            Long liquorPriKey
    ) {
        try {
            // 술
            Optional<LiquorDto> liquorDto = liquorRepository.findByPriKey(liquorPriKey);
            if (liquorDto.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
            // 레시피
            Optional<LiquorRecipeDto> liquorRecipeDto = liquorRecipeRepository.findByLiquorPriKey(liquorPriKey);
            // 도수
            Optional<LiquorAbvDto> liquorAbvDto = Optional.empty();
            if (liquorDto.get().getLiquorAbvId() != null) {
                liquorAbvDto = liquorAbvRepository.findByPriKey(liquorDto.get().getLiquorAbvId());
            }
            // 2차 분류
            Optional<LiquorDetailDto> liquorDetailDto = Optional.empty();
            if (liquorDto.get().getLiquorDetailId() != null) {
                liquorDetailDto = liquorDetailRepository.findByPriKey(liquorDto.get().getLiquorDetailId());
            }
            // 주량
            Optional<DrinkingCapacityDto> drinkingCapacityDto = Optional.empty();
            if (liquorDto.get().getDrinkingCapacityId() != null) {
                drinkingCapacityDto = drinkingCapacityRepository.findByPriKey(liquorDto.get().getDrinkingCapacityId());
            }
            // 1차 분류
            Optional<LiquorNameDto> liquorNameDto = Optional.empty();
            if (liquorDto.get().getLiquorNameId() != null) {
                liquorNameDto = liquorNameRepository.findByPriKey(liquorDto.get().getLiquorNameId());
            }
            // 안주
            List<LiquorSnackDto> liquorSnackDtos = liquorSnackRepository.findByLiquorPriKey(liquorPriKey);
            // 판매처
            List<LiquorSellDto> liquorSellDtos = liquorSellRepository.findByLiquorPriKey(liquorPriKey);
            // 재료
            List<LiquorMaterialDto> liquorMaterialDtos = liquorMaterialRepository.findByLiquorPriKey(liquorPriKey);
            // 기분
            List<StateTypeDto> stateTypeDtos = stateTypeRepository.findByLiquorPriKey(liquorPriKey);
            // 맛
            List<TasteTypeDto> tasteTypeDtos = tasteTypeRepository.findByLiquorPriKey(liquorPriKey);

            return LiquorTotalRes.of(
                    liquorDto.get(),
                    liquorRecipeDto.orElse(null),
                    liquorAbvDto.orElse(null),
                    liquorDetailDto.orElse(null),
                    drinkingCapacityDto.orElse(null),
                    liquorNameDto.orElse(null),
                    liquorSnackDtos,
                    liquorSellDtos,
                    liquorMaterialDtos,
                    stateTypeDtos,
                    tasteTypeDtos
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
