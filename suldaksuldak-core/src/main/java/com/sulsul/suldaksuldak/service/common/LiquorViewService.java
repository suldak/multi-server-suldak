package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import com.sulsul.suldaksuldak.dto.tag.StateTypeDto;
import com.sulsul.suldaksuldak.dto.tag.TasteTypeDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
    private final LiquorDataService liquorDataService;
    private final LiquorRepository liquorRepository;
    private final SnToLiRepository snToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;
    private final MtToLiRepository mtToLiRepository;

    public Page<LiquorTotalRes> getLiquorByTag(
            LiquorTagSearchDto liquorTagSearchDto,
            Pageable pageable
    ) {
        try {
            List<Long> resultLiquorPriKey = liquorRepository.findAllLiquorPriKey();
            if (liquorTagSearchDto.getSearchTag() != null && !liquorTagSearchDto.getSearchTag().isBlank()) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findBySearchTag(liquorTagSearchDto.getSearchTag())
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorAbvPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorAbvPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorAbvPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorDetailPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorDetailPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorDetailPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getDrinkingCapacityPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByDrinkingCapacityPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getDrinkingCapacityPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorNamePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorNamePriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorNamePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSnackPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        snToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getSnackPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSellPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        slToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getSellPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getStatePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        stToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getStatePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getTastePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        ttToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getTastePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getMaterialPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        mtToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getMaterialPriKeys()
                        )
                );
            }
            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
            log.info(resultLiquorPriKey.toString());
            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
                    pageable,
                    resultLiquorPriKey
            );
            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
            for (LiquorDto res: liquorDto.getContent()) {
                liquorTotalRes.add(liquorDataService.getLiquorTotalData(res));
            }
            return new PageImpl<>(
                    liquorTotalRes,
                    liquorDto.getPageable(),
                    liquorDto.getTotalElements()
            );
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
                resultContent.add(liquorDataService.getLiquorTotalData(liquorPriKey));
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

    /**
     * 술에 해당하는 태그들을 조합하여 해당하는 술 조회
     */
    public Page<LiquorTotalRes> getLiquorListByLiquor(
            List<UserLiquorTagDto> liquorPriKeyList,
            Pageable pageable
    ) {
        try {
            HashMap<Long, Integer> liquorAbvPriKeyList =
                    UtilTool.generateCountedHashMap(
                            liquorPriKeyList
                                    .stream()
                                    .map(UserLiquorTagDto::getLiquorAbvPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
            HashMap<Long, Integer> liquorDetailPriKeyList =
                    UtilTool.generateCountedHashMap(
                            liquorPriKeyList
                                    .stream()
                                    .map(UserLiquorTagDto::getLiquorDetailPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
            HashMap<Long, Integer> drinkingCapacityPriKey =
                    UtilTool.generateCountedHashMap(
                            liquorPriKeyList
                                    .stream()
                                    .map(UserLiquorTagDto::getDrinkingCapacityPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
            HashMap<Long, Integer> liquorNamePriKeyList =
                    UtilTool.generateCountedHashMap(
                            liquorPriKeyList
                                    .stream()
                                    .map(UserLiquorTagDto::getLiquorNamePriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
            HashMap<Long, Integer> liquorSnackPriKeyList =
                    UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList
                                            .stream()
                                            .map(dto -> {
                                                List<LiquorSnackDto> liquorSnackDtos = liquorDataService.getLiquorSnackDtoList(dto.getLiquorId());
                                                return liquorSnackDtos.stream()
                                                        .map(LiquorSnackDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    );
            HashMap<Long, Integer> statePriKeyList =
                    UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList
                                            .stream()
                                            .map(dto -> {
                                                List<StateTypeDto> stateTypeDtos = liquorDataService.getStateTypeDtoList(dto.getLiquorId());
                                                return stateTypeDtos.stream()
                                                        .map(StateTypeDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    );
            HashMap<Long, Integer> tastePriKeyList =
                    UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList
                                            .stream()
                                            .map(dto -> {
                                                List<TasteTypeDto> tasteTypeDtos = liquorDataService.getTasteTypeDtoList(dto.getLiquorId());
                                                return tasteTypeDtos.stream()
                                                        .map(TasteTypeDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    );

            // 데이터 조회
            List<Long> resultLiquorPriKey = new ArrayList<>();
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList))) {
                log.info("liquorAbvPriKeyList >> " + liquorAbvPriKeyList);
                resultLiquorPriKey.addAll(
                        liquorRepository.findByLiquorAbvPriKey(
                                UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList))) {
                log.info("liquorDetailPriKeyList >> " + liquorDetailPriKeyList);
                resultLiquorPriKey.addAll(
                        liquorRepository.findByLiquorDetailPriKey(
                                UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey))) {
                log.info("drinkingCapacityPriKey >> " + drinkingCapacityPriKey);
                resultLiquorPriKey.addAll(
                        liquorRepository.findByDrinkingCapacityPriKey(
                                UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorNamePriKeyList))) {
                log.info("liquorNamePriKeyList >> " + liquorNamePriKeyList);
                resultLiquorPriKey.addAll(
                        liquorRepository.findByLiquorNamePriKey(
                                UtilTool.selectKeysWithHighValues(liquorNamePriKeyList)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList))) {
                log.info("liquorSnackPriKeyList >> " + liquorSnackPriKeyList);
                resultLiquorPriKey.addAll(
                        snToLiRepository.findLiquorPriKeyByTagPriKey(
                                UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(statePriKeyList))) {
                log.info("statePriKeyList >> " + statePriKeyList);
                resultLiquorPriKey.addAll(
                        stToLiRepository.findLiquorPriKeyByTagPriKey(
                                UtilTool.selectKeysWithHighValues(statePriKeyList)
                        )
                );
            }
            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(tastePriKeyList))) {
                log.info("tastePriKeyList >> " + tastePriKeyList);
                resultLiquorPriKey.addAll(
                        ttToLiRepository.findLiquorPriKeyByTagPriKey(
                                UtilTool.selectKeysWithHighValues(tastePriKeyList)
                        )
                );
            }
            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
            log.info(resultLiquorPriKey.toString());
            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
                    pageable,
                    resultLiquorPriKey
            );
            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
            for (LiquorDto res: liquorDto.getContent()) {
                liquorTotalRes.add(liquorDataService.getLiquorTotalData(res));
            }
            return new PageImpl<>(
                    liquorTotalRes,
                    liquorDto.getPageable(),
                    liquorDto.getTotalElements()
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
