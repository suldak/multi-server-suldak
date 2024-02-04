package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.party.tag.PartyTagDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.party.tag.PartyTagRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagViewService {
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final LiquorNameRepository liquorNameRepository;
    private final LiquorSellRepository liquorSellRepository;
    private final StateTypeRepository stateTypeRepository;
    private final TasteTypeRepository tasteTypeRepository;
    private final LiquorSnackRepository liquorSnackRepository;
    private final PartyTagRepository partyTagRepository;

    /**
     * 모든 주량 조회
     */
    public List<DrinkingCapacityDto> getDrinkingCapacityDtoList() {
        try {
            return drinkingCapacityRepository.findAll()
                    .stream()
                    .map(DrinkingCapacityDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 도수 조회
     */
    public List<LiquorAbvDto> getLiquorAbvDtoList() {
        try {
            return liquorAbvRepository.findAll()
                    .stream()
                    .map(LiquorAbvDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 2차 분류 조회
     */
    public List<LiquorDetailDto> getLiquorDetailDtoList() {
        try {
            return liquorDetailRepository.findAll()
                    .stream()
                    .map(LiquorDetailDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 재료 조회
     */
    public List<LiquorMaterialDto> getLiquorMaterialDtoList() {
        try {
            return liquorMaterialRepository.findAll()
                    .stream()
                    .map(LiquorMaterialDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 1차 분류 조회
     */
    public List<LiquorNameDto> getLiquorNameDtoList() {
        try {
            return liquorNameRepository.findAll()
                    .stream()
                    .map(LiquorNameDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 판매처 조회
     */
    public List<LiquorSellDto> getLiquorSellDtoList() {
        try {
            return liquorSellRepository.findAll()
                    .stream()
                    .map(LiquorSellDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 상태 조회
     */
    public List<StateTypeDto> getStateTypeDtoList() {
        try {
            return stateTypeRepository.findAll()
                    .stream()
                    .map(StateTypeDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 맛 조회
     */
    public List<TasteTypeDto> getTasteTypeDtoList() {
        try {
            return tasteTypeRepository.findAll()
                    .stream()
                    .map(TasteTypeDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 모든 추천 안주 조회
     */
    public List<LiquorSnackDto> getLiquorSnackDtoList() {
        try {
            return liquorSnackRepository.findAllLiquorSnack();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public List<PartyTagDto> getPartyTagDtoList() {
        try {
            return partyTagRepository.findAll()
                    .stream()
                    .map(PartyTagDto::of)
                    .toList();
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
