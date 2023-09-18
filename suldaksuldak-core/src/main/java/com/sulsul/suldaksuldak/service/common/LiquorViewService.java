package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.recipe.LiquorRecipeDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.recipe.LiquorRecipeRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.liquor.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.tag.material.LiquorMaterialRepository;
import com.sulsul.suldaksuldak.repo.liquor.name.LiquorNameRepository;
import com.sulsul.suldaksuldak.repo.tag.sell.LiquorSellRepository;
import com.sulsul.suldaksuldak.repo.tag.state.StateTypeRepository;
import com.sulsul.suldaksuldak.repo.tag.taste.TasteTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final SnToLiRepository snToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;
    private final MtToLiRepository mtToLiRepository;

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

    public List<LiquorDto> getLiquorByTag(
            LiquorTotalReq liquorTotalReq
    ) {
        try {
            List<Long> resultLiquorPriKey = new ArrayList<>();
            if (checkLongList(liquorTotalReq.getSnackPriKeys())) {
                resultLiquorPriKey = snToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getSnackPriKeys()
                );
            }
            if (checkLongList(liquorTotalReq.getSellPriKeys())) {
                resultLiquorPriKey = slToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getSellPriKeys()
                );
            }
            if (checkLongList(liquorTotalReq.getStatePriKeys())) {
                resultLiquorPriKey = stToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getStatePriKeys()
                );
            }
            if (checkLongList(liquorTotalReq.getTastePriKeys())) {
                resultLiquorPriKey = ttToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getTastePriKeys()
                );
            }
            if (checkLongList(liquorTotalReq.getMaterialPriKeys())) {
                resultLiquorPriKey = mtToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getMaterialPriKeys()
                );
            }
            if (liquorTotalReq.getLiquorAbvId() != null) {
                resultLiquorPriKey = liquorRepository.findByLiquorAbvPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorAbvId()
                );
            }
            if (liquorTotalReq.getLiquorDetailId() != null) {
                resultLiquorPriKey = liquorRepository.findByLiquorDetailPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorDetailId()
                );
            }
            if (liquorTotalReq.getDrinkingCapacityId() != null) {
                resultLiquorPriKey = liquorRepository.findByDrinkingCapacityPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getDrinkingCapacityId()
                );
            }
            if (liquorTotalReq.getLiquorNameId() != null) {
                resultLiquorPriKey = liquorRepository.findByLiquorNamePriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorNameId()
                );
            }

            List<LiquorDto> resultLiquorDto = new ArrayList<>();
            for (Long key: resultLiquorPriKey) {
                Optional<LiquorDto> liquorDto = liquorRepository.findByPriKey(key);
                liquorDto.ifPresent(resultLiquorDto::add);
            }
            return resultLiquorDto;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private Boolean checkLongList(List<Long> longList) {
        return longList != null && !longList.isEmpty();
    }
}
