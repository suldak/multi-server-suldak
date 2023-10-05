package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorRes;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
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

    private final SnToLiRepository snToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;
    private final MtToLiRepository mtToLiRepository;

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
            if (liquorTotalReq.getSearchTag() != null && !liquorTotalReq.getSearchTag().isBlank()) {
//                resultLiquorPriKey = liquorRepository.findBySearchTag(liquorTotalReq.getSearchTag());
                resultLiquorPriKey.addAll(liquorRepository.findBySearchTag(liquorTotalReq.getSearchTag()));
            }
            if (checkLongList(liquorTotalReq.getSnackPriKeys())) {
//                resultLiquorPriKey = snToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getSnackPriKeys()
//                );
                resultLiquorPriKey.addAll(snToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getSnackPriKeys()
                ));
            }
            if (checkLongList(liquorTotalReq.getSellPriKeys())) {
//                resultLiquorPriKey = slToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getSellPriKeys()
//                );
                resultLiquorPriKey.addAll(slToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getSellPriKeys()
                ));
            }
            if (checkLongList(liquorTotalReq.getStatePriKeys())) {
//                resultLiquorPriKey = stToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getStatePriKeys()
//                );
                resultLiquorPriKey.addAll(stToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getStatePriKeys()
                ));
            }
            if (checkLongList(liquorTotalReq.getTastePriKeys())) {
//                resultLiquorPriKey = ttToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getTastePriKeys()
//                );
                resultLiquorPriKey.addAll(ttToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getTastePriKeys()
                ));
            }
            if (checkLongList(liquorTotalReq.getMaterialPriKeys())) {
//                resultLiquorPriKey = mtToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getMaterialPriKeys()
//                );
                resultLiquorPriKey.addAll(mtToLiRepository.findLiquorPriKeyByTagPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getMaterialPriKeys()
                ));
            }
            if (liquorTotalReq.getLiquorAbvId() != null) {
//                resultLiquorPriKey = liquorRepository.findByLiquorAbvPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getLiquorAbvId()
//                );
                resultLiquorPriKey.addAll(liquorRepository.findByLiquorAbvPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorAbvId()
                ));
            }
            if (liquorTotalReq.getLiquorDetailId() != null) {
//                resultLiquorPriKey = liquorRepository.findByLiquorDetailPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getLiquorDetailId()
//                );
                resultLiquorPriKey.addAll(liquorRepository.findByLiquorDetailPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorDetailId()
                ));
            }
            if (liquorTotalReq.getDrinkingCapacityId() != null) {
//                resultLiquorPriKey = liquorRepository.findByDrinkingCapacityPriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getDrinkingCapacityId()
//                );
                resultLiquorPriKey.addAll(liquorRepository.findByDrinkingCapacityPriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getDrinkingCapacityId()
                ));
            }
            if (liquorTotalReq.getLiquorNameId() != null) {
//                resultLiquorPriKey = liquorRepository.findByLiquorNamePriKey(
//                        resultLiquorPriKey,
//                        liquorTotalReq.getLiquorNameId()
//                );
                resultLiquorPriKey.addAll(liquorRepository.findByLiquorNamePriKey(
                        resultLiquorPriKey,
                        liquorTotalReq.getLiquorNameId()
                ));
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

    public Page<LiquorTotalRes> getLatestLiquor(
            Pageable pageable
    ) {
        try {
            Page<Long> liquorDtos = liquorRepository.findByCreatedLatest(pageable);
            List<LiquorTotalRes> resultContent = new ArrayList<>();
            for (Long liquorPriKey: liquorDtos.getContent()) {
                resultContent.add(getLiquorTotalData(liquorPriKey));
            }
            return new PageImpl<>(
                    resultContent,
                    liquorDtos.getPageable(),
                    liquorDtos.getTotalElements()
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private Boolean checkLongList(List<Long> longList) {
        return longList != null && !longList.isEmpty();
    }

    private List<Long> unionLists(List<Long> list1, List<Long> list2) {
        // Create a Set to store unique elements from both lists
        Set<Long> uniqueSet = new HashSet<>();

        // Add elements from the first list to the set
        uniqueSet.addAll(list1);

        // Add elements from the second list to the set
        uniqueSet.addAll(list2);

        // Create a new ArrayList for the result
        return new ArrayList<>(uniqueSet);
    }

    private List<Long> removeDuplicates(List<Long> inputList) {
        HashSet<Long> uniqueSet = new HashSet<>(inputList);
        return new ArrayList<>(uniqueSet);
    }
}
