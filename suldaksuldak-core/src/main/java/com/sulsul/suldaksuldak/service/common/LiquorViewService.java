package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
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
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Page<LiquorTotalRes> getLiquorByTag(
            LiquorTagSearchDto liquorTagSearchDto,
            Pageable pageable
    ) {
        try {
            log.info(liquorTagSearchDto.toString());
            return liquorRepository.findByTags(
                    pageable,
                    liquorTagSearchDto.getSnackPriKeys(),
                    liquorTagSearchDto.getSellPriKeys(),
                    liquorTagSearchDto.getMaterialPriKeys(),
                    liquorTagSearchDto.getStatePriKeys(),
                    liquorTagSearchDto.getTastePriKeys(),
                    liquorTagSearchDto.getLiquorAbvPriKeys(),
                    liquorTagSearchDto.getLiquorDetailPriKeys(),
                    liquorTagSearchDto.getDrinkingCapacityPriKeys(),
                    liquorTagSearchDto.getLiquorNamePriKeys(),
                    liquorTagSearchDto.getSearchTag()
            );
//            List<Long> resultLiquorPriKey = new ArrayList<>();
//            if (liquorTagSearchDto.getSearchTag() != null && !liquorTagSearchDto.getSearchTag().isBlank()) {
//                resultLiquorPriKey.addAll(liquorRepository.findBySearchTag(liquorTagSearchDto.getSearchTag()));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getSnackPriKeys())) {
//                resultLiquorPriKey.addAll(snToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getSnackPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getSellPriKeys())) {
//                resultLiquorPriKey.addAll(slToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getSellPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getStatePriKeys())) {
//                resultLiquorPriKey.addAll(stToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getStatePriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getTastePriKeys())) {
//                resultLiquorPriKey.addAll(ttToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getTastePriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getMaterialPriKeys())) {
//                resultLiquorPriKey.addAll(mtToLiRepository.findLiquorPriKeyByTagPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getMaterialPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorAbvPriKeys())) {
//                resultLiquorPriKey.addAll(liquorRepository.findByLiquorAbvPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getLiquorAbvPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorDetailPriKeys())) {
//                resultLiquorPriKey.addAll(liquorRepository.findByLiquorDetailPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getLiquorDetailPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getDrinkingCapacityPriKeys())) {
//                resultLiquorPriKey.addAll(liquorRepository.findByDrinkingCapacityPriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getDrinkingCapacityPriKeys()
//                ));
//            }
//            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorNamePriKeys())) {
//                resultLiquorPriKey.addAll(liquorRepository.findByLiquorNamePriKey(
//                        resultLiquorPriKey,
//                        liquorTagSearchDto.getLiquorNamePriKeys()
//                ));
//            }
//
//            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
//            List<LiquorTotalRes> resultLiquorDto = new ArrayList<>();
//            for (Long key: resultLiquorPriKey) {
//                resultLiquorDto.add(getLiquorTotalData(key));
//            }
//            return resultLiquorDto;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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

//    public List<LiquorTotalRes> getLiquorListByLiquorPriKey(
//            List<Long> liquorPriKeyList
//    ) {
//        try {
//            // 1 술 조회
//            LiquorTagSearchDto liquorTagSearchDto = LiquorTagSearchDto.emptyListOf();
//            for (Long liquorPriKey: liquorPriKeyList) {
//                liquorTagSearchDto.addTags(getLiquorTotalData(liquorPriKey));
//            }
//            return getLiquorByTag(liquorTagSearchDto);
//        } catch (GeneralException e) {
//            throw new GeneralException(e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
//        }
//    }
}
