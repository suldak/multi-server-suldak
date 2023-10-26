package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.liquor.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.name.LiquorNameRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.tag.material.LiquorMaterialRepository;
import com.sulsul.suldaksuldak.repo.tag.sell.LiquorSellRepository;
import com.sulsul.suldaksuldak.repo.tag.state.StateTypeRepository;
import com.sulsul.suldaksuldak.repo.tag.taste.TasteTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiquorDataService {
    private final LiquorRepository liquorRepository;
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
     * 술에 관련된 모든 데이터 조회
     */
    public LiquorTotalRes getLiquorTotalData(
            Long liquorPriKey
    ) {
        try {
            if (liquorPriKey == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND LIQUOR PRI KEY");
            }
            // 술
            Optional<LiquorDto> liquorDto = liquorRepository.findByPriKey(liquorPriKey);
            if (liquorDto.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
            // TODO 비동기 처리
            return LiquorTotalRes.of(
                    liquorDto.get(),
                    getLiquorAbvDto(liquorDto.get().getLiquorAbvId()),
                    getLiquorDetailDto(liquorDto.get().getLiquorDetailId()),
                    getDrinkingCapacityDto(liquorDto.get().getDrinkingCapacityId()),
                    getLiquorNameDto(liquorDto.get().getLiquorNameId()),
                    getLiquorSnackDtoList(liquorDto.get().getId()),
                    getLiquorSellDtoList(liquorDto.get().getId()),
                    getLiquorMaterialDtoList(liquorDto.get().getId()),
                    getStateTypeDtoList(liquorDto.get().getId()),
                    getTasteTypeDtoList(liquorDto.get().getId())
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public LiquorTotalRes getLiquorTotalData(
            LiquorDto liquorDto
    ) {
        try {
            // TODO 비동기 처리
            return LiquorTotalRes.of(
                    liquorDto,
                    getLiquorAbvDto(liquorDto.getLiquorAbvId()),
                    getLiquorDetailDto(liquorDto.getLiquorDetailId()),
                    getDrinkingCapacityDto(liquorDto.getDrinkingCapacityId()),
                    getLiquorNameDto(liquorDto.getLiquorNameId()),
                    getLiquorSnackDtoList(liquorDto.getId()),
                    getLiquorSellDtoList(liquorDto.getId()),
                    getLiquorMaterialDtoList(liquorDto.getId()),
                    getStateTypeDtoList(liquorDto.getId()),
                    getTasteTypeDtoList(liquorDto.getId())
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 도수 정보 가져오기
     */
    public Optional<LiquorAbvDto> getLiquorAbvDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorAbvRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 2차 분류 종류 가져오기
     */
    public Optional<LiquorDetailDto> getLiquorDetailDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorDetailRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 주량 정보 가져오기
     */
    public Optional<DrinkingCapacityDto> getDrinkingCapacityDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return drinkingCapacityRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 1차 분류 정보 가져오기
     */
    public Optional<LiquorNameDto> getLiquorNameDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorNameRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 안주 태그 목록 (RES) 가져오기
     */
    public List<LiquorSnackRes> getLiquorSnackResList(
            Long liquorPriKey
    ) {
        try {
            return liquorSnackRepository
                    .findByLiquorPriKey(liquorPriKey).stream().map(LiquorSnackRes::from).toList();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 안주 태그 목록 (DTO) 가져오기
     */
    public List<LiquorSnackDto> getLiquorSnackDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorSnackRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 판매처 태그 목록 가져오기
     */
    public List<LiquorSellDto> getLiquorSellDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorSellRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 재료 태그 목록 가져오기
     */
    public List<LiquorMaterialDto> getLiquorMaterialDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorMaterialRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 상태 태그 목록 가져오기
     */
    public List<StateTypeDto> getStateTypeDtoList(
            Long liquorPriKey
    ) {
        try {
            return stateTypeRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 맛 태그 목록 가져오기
     */
    public List<TasteTypeDto> getTasteTypeDtoList(
            Long liquorPriKey
    ) {
        try {
            return tasteTypeRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
