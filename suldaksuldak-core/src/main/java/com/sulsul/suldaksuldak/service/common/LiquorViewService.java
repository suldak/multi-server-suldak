package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import com.sulsul.suldaksuldak.dto.tag.StateTypeDto;
import com.sulsul.suldaksuldak.dto.tag.TasteTypeDto;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.service.search.SearchService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
    private final SearchService searchService;
    private final LiquorDataService liquorDataService;
    private final LiquorRepository liquorRepository;
    private final SnToLiRepository snToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;
    private final MtToLiRepository mtToLiRepository;

    public Page<LiquorTotalRes> getLiquorByTag(
            Long userPriKey,
            LiquorTagSearchDto liquorTagSearchDto,
            Pageable pageable
    ) {
        // TODO 병렬 처리
        Boolean andBool = liquorTagSearchDto.getAndBool() == null ? true : liquorTagSearchDto.getAndBool();
        try {
            List<Long> resultLiquorPriKey = liquorRepository.findAllLiquorPriKey();
            if (liquorTagSearchDto.getSearchTag() != null && !liquorTagSearchDto.getSearchTag().isBlank()) {
                // =============
                if (userPriKey != null)
                    searchService.createSearchLog(
                            userPriKey,
                            liquorTagSearchDto.getSearchTag()
                    );
                // =============
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            liquorRepository.findBySearchTag(liquorTagSearchDto.getSearchTag())
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            liquorRepository.findBySearchTag(liquorTagSearchDto.getSearchTag())
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorAbvPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorAbvPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorAbvPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorAbvPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorAbvPriKeys()
                            )
                    );
                }

            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorDetailPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorDetailPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorDetailPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorDetailPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorDetailPriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getDrinkingCapacityPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            liquorRepository.findByDrinkingCapacityPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getDrinkingCapacityPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            liquorRepository.findByDrinkingCapacityPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getDrinkingCapacityPriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorNamePriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorNamePriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorNamePriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            liquorRepository.findByLiquorNamePriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getLiquorNamePriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSnackPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            snToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getSnackPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            snToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getSnackPriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSellPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            slToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getSellPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            slToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getSellPriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getStatePriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            stToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getStatePriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            stToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getStatePriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getTastePriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            ttToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getTastePriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            ttToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getTastePriKeys()
                            )
                    );
                }
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getMaterialPriKeys())) {
                if (andBool) {
                    resultLiquorPriKey = UtilTool.findOverlappingElements(
                            resultLiquorPriKey,
                            mtToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getMaterialPriKeys()
                            )
                    );
                } else {
                    resultLiquorPriKey = UtilTool.unionLists(
                            resultLiquorPriKey,
                            mtToLiRepository.findLiquorPriKeyByTagPriKey(
                                    resultLiquorPriKey,
                                    liquorTagSearchDto.getMaterialPriKeys()
                            )
                    );
                }
            }
            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
            log.info(resultLiquorPriKey.toString());
            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
                    pageable,
                    resultLiquorPriKey
            );
            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
            for (LiquorDto res: liquorDto.getContent()) {
                liquorTotalRes.add(liquorDataService.getLiquorTotalData(res, userPriKey));
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
            Pageable pageable,
            Long userPriKey
    ) {
        try {
            Page<Long> liquorDtos = liquorRepository.findByCreatedLatest(pageable);
            List<LiquorTotalRes> resultContent = new ArrayList<>();
            for (Long liquorPriKey: liquorDtos.getContent()) {
                resultContent.add(liquorDataService.getLiquorTotalData(liquorPriKey, userPriKey));
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
            Pageable pageable,
            Long userPriKey
    ) {
        try {
            // 각 해시맵 생성을 비동기적으로 처리
            CompletableFuture<HashMap<Long, Integer>> liquorAbvPriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            liquorPriKeyList.stream()
                                    .map(UserLiquorTagDto::getLiquorAbvPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    ));
            CompletableFuture<HashMap<Long, Integer>> liquorDetailPriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            liquorPriKeyList.stream()
                                    .map(UserLiquorTagDto::getLiquorDetailPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    ));
            CompletableFuture<HashMap<Long, Integer>> drinkingCapacityPriKeyFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            liquorPriKeyList.stream()
                                    .map(UserLiquorTagDto::getDrinkingCapacityPriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    ));
            CompletableFuture<HashMap<Long, Integer>> liquorNamePriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            liquorPriKeyList.stream()
                                    .map(UserLiquorTagDto::getLiquorNamePriKey)
                                    .filter(Objects::nonNull)
                                    .toList()
                    ));
            CompletableFuture<HashMap<Long, Integer>> liquorSnackPriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList.stream()
                                            .map(dto -> {
                                                List<LiquorSnackDto> liquorSnackDtos =
                                                        liquorDataService.getLiquorSnackDtoList(dto.getLiquorId());
                                                return liquorSnackDtos.stream()
                                                        .map(LiquorSnackDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    ));
            CompletableFuture<HashMap<Long, Integer>> statePriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList.stream()
                                            .map(dto -> {
                                                List<StateTypeDto> stateTypeDtos =
                                                        liquorDataService.getStateTypeDtoList(dto.getLiquorId());
                                                return stateTypeDtos.stream()
                                                        .map(StateTypeDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    ));
            CompletableFuture<HashMap<Long, Integer>> tastePriKeyListFuture =
                    CompletableFuture.supplyAsync(() -> UtilTool.generateCountedHashMap(
                            UtilTool.removeDuplicates(
                                    liquorPriKeyList.stream()
                                            .map(dto -> {
                                                List<TasteTypeDto> tasteTypeDtos =
                                                        liquorDataService.getTasteTypeDtoList(dto.getLiquorId());
                                                return tasteTypeDtos.stream()
                                                        .map(TasteTypeDto::getId)
                                                        .toList();
                                            })
                                            .flatMap(List::stream)
                                            .toList()
                            )
                    ));

            // 모든 해시맵 생성 작업이 완료될 때까지 기다림
            CompletableFuture<Void> allHashmapsFuture = CompletableFuture.allOf(
                    liquorAbvPriKeyListFuture,
                    liquorDetailPriKeyListFuture,
                    drinkingCapacityPriKeyFuture,
                    liquorNamePriKeyListFuture,
                    liquorSnackPriKeyListFuture,
                    statePriKeyListFuture,
                    tastePriKeyListFuture
            );

            allHashmapsFuture.join();
            HashMap<Long, Integer> liquorAbvPriKeyList = liquorAbvPriKeyListFuture.join();
            HashMap<Long, Integer> liquorDetailPriKeyList = liquorDetailPriKeyListFuture.join();
            HashMap<Long, Integer> drinkingCapacityPriKey = drinkingCapacityPriKeyFuture.join();
            HashMap<Long, Integer> liquorNamePriKeyList = liquorNamePriKeyListFuture.join();
            HashMap<Long, Integer> liquorSnackPriKeyList = liquorSnackPriKeyListFuture.join();
            HashMap<Long, Integer> statePriKeyList = statePriKeyListFuture.join();
            HashMap<Long, Integer> tastePriKeyList = tastePriKeyListFuture.join();

            // 비동기적으로 결과 조회
            CompletableFuture<List<Long>> liquorKeysFuture = CompletableFuture.supplyAsync(() -> {
                List<CompletableFuture<List<Long>>> resultLiquorKeysFutures = List.of(
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList)) ?
                                liquorRepository.findByLiquorAbvPriKey(UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList)) ?
                                liquorRepository.findByLiquorDetailPriKey(UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey)) ?
                                liquorRepository.findByDrinkingCapacityPriKey(UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorNamePriKeyList)) ?
                                liquorRepository.findByLiquorNamePriKey(UtilTool.selectKeysWithHighValues(liquorNamePriKeyList)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList)) ?
                                snToLiRepository.findLiquorPriKeyByTagPriKey(UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(statePriKeyList)) ?
                                stToLiRepository.findLiquorPriKeyByTagPriKey(UtilTool.selectKeysWithHighValues(statePriKeyList)) :
                                Collections.emptyList()),
                        CompletableFuture.supplyAsync(() ->
                                UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(tastePriKeyList)) ?
                                ttToLiRepository.findLiquorPriKeyByTagPriKey(UtilTool.selectKeysWithHighValues(tastePriKeyList)) :
                                Collections.emptyList())
                );

                CompletableFuture<Void> allResultsFuture = CompletableFuture.allOf(
                        resultLiquorKeysFutures.toArray(new CompletableFuture[0])
                );

                return allResultsFuture.thenApply(ignored ->
                        resultLiquorKeysFutures.stream()
                                .map(CompletableFuture::join)
                                .flatMap(List::stream)
                                .collect(Collectors.toList())
                ).join();
            });

            List<Long> resultLiquorKeys = liquorKeysFuture.join();

            // 결과 처리와 반환
            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
                    pageable,
                    resultLiquorKeys
            );

            List<LiquorTotalRes> liquorTotalRes = liquorDto.getContent().stream()
                    .map(res -> liquorDataService.getLiquorTotalData(res, userPriKey))
                    .collect(Collectors.toList());

            return new PageImpl<>(liquorTotalRes, pageable, liquorDto.getTotalElements());
//            HashMap<Long, Integer> liquorAbvPriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            liquorPriKeyList
//                                    .stream()
//                                    .map(UserLiquorTagDto::getLiquorAbvPriKey)
//                                    .filter(Objects::nonNull)
//                                    .toList()
//                    );
//            HashMap<Long, Integer> liquorDetailPriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            liquorPriKeyList
//                                    .stream()
//                                    .map(UserLiquorTagDto::getLiquorDetailPriKey)
//                                    .filter(Objects::nonNull)
//                                    .toList()
//                    );
//            HashMap<Long, Integer> drinkingCapacityPriKey =
//                    UtilTool.generateCountedHashMap(
//                            liquorPriKeyList
//                                    .stream()
//                                    .map(UserLiquorTagDto::getDrinkingCapacityPriKey)
//                                    .filter(Objects::nonNull)
//                                    .toList()
//                    );
//            HashMap<Long, Integer> liquorNamePriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            liquorPriKeyList
//                                    .stream()
//                                    .map(UserLiquorTagDto::getLiquorNamePriKey)
//                                    .filter(Objects::nonNull)
//                                    .toList()
//                    );
//            HashMap<Long, Integer> liquorSnackPriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            UtilTool.removeDuplicates(
//                                    liquorPriKeyList
//                                            .stream()
//                                            .map(dto -> {
//                                                List<LiquorSnackDto> liquorSnackDtos = liquorDataService.getLiquorSnackDtoList(dto.getLiquorId());
//                                                return liquorSnackDtos.stream()
//                                                        .map(LiquorSnackDto::getId)
//                                                        .toList();
//                                            })
//                                            .flatMap(List::stream)
//                                            .toList()
//                            )
//                    );
//            HashMap<Long, Integer> statePriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            UtilTool.removeDuplicates(
//                                    liquorPriKeyList
//                                            .stream()
//                                            .map(dto -> {
//                                                List<StateTypeDto> stateTypeDtos = liquorDataService.getStateTypeDtoList(dto.getLiquorId());
//                                                return stateTypeDtos.stream()
//                                                        .map(StateTypeDto::getId)
//                                                        .toList();
//                                            })
//                                            .flatMap(List::stream)
//                                            .toList()
//                            )
//                    );
//            HashMap<Long, Integer> tastePriKeyList =
//                    UtilTool.generateCountedHashMap(
//                            UtilTool.removeDuplicates(
//                                    liquorPriKeyList
//                                            .stream()
//                                            .map(dto -> {
//                                                List<TasteTypeDto> tasteTypeDtos = liquorDataService.getTasteTypeDtoList(dto.getLiquorId());
//                                                return tasteTypeDtos.stream()
//                                                        .map(TasteTypeDto::getId)
//                                                        .toList();
//                                            })
//                                            .flatMap(List::stream)
//                                            .toList()
//                            )
//                    );
//
//            // 데이터 조회
//            List<Long> resultLiquorPriKey = new ArrayList<>();
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList))) {
//                log.info("liquorAbvPriKeyList >> " + liquorAbvPriKeyList);
//                resultLiquorPriKey.addAll(
//                        liquorRepository.findByLiquorAbvPriKey(
//                                UtilTool.selectKeysWithHighValues(liquorAbvPriKeyList)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList))) {
//                log.info("liquorDetailPriKeyList >> " + liquorDetailPriKeyList);
//                resultLiquorPriKey.addAll(
//                        liquorRepository.findByLiquorDetailPriKey(
//                                UtilTool.selectKeysWithHighValues(liquorDetailPriKeyList)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey))) {
//                log.info("drinkingCapacityPriKey >> " + drinkingCapacityPriKey);
//                resultLiquorPriKey.addAll(
//                        liquorRepository.findByDrinkingCapacityPriKey(
//                                UtilTool.selectKeysWithHighValues(drinkingCapacityPriKey)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorNamePriKeyList))) {
//                log.info("liquorNamePriKeyList >> " + liquorNamePriKeyList);
//                resultLiquorPriKey.addAll(
//                        liquorRepository.findByLiquorNamePriKey(
//                                UtilTool.selectKeysWithHighValues(liquorNamePriKeyList)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList))) {
//                log.info("liquorSnackPriKeyList >> " + liquorSnackPriKeyList);
//                resultLiquorPriKey.addAll(
//                        snToLiRepository.findLiquorPriKeyByTagPriKey(
//                                UtilTool.selectKeysWithHighValues(liquorSnackPriKeyList)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(statePriKeyList))) {
//                log.info("statePriKeyList >> " + statePriKeyList);
//                resultLiquorPriKey.addAll(
//                        stToLiRepository.findLiquorPriKeyByTagPriKey(
//                                UtilTool.selectKeysWithHighValues(statePriKeyList)
//                        )
//                );
//            }
//            if (UtilTool.checkLongList(UtilTool.selectKeysWithHighValues(tastePriKeyList))) {
//                log.info("tastePriKeyList >> " + tastePriKeyList);
//                resultLiquorPriKey.addAll(
//                        ttToLiRepository.findLiquorPriKeyByTagPriKey(
//                                UtilTool.selectKeysWithHighValues(tastePriKeyList)
//                        )
//                );
//            }
//            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
//            log.info(resultLiquorPriKey.toString());
//            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
//                    pageable,
//                    resultLiquorPriKey
//            );
//            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
//            for (LiquorDto res: liquorDto.getContent()) {
//                liquorTotalRes.add(liquorDataService.getLiquorTotalData(res, userPriKey));
//            }
//            return new PageImpl<>(
//                    liquorTotalRes,
//                    liquorDto.getPageable(),
//                    liquorDto.getTotalElements()
//            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Page<LiquorTotalRes> getLiquorListByLike(
            Page<LiquorLikeDto> liquorLikeDtos,
            Long userPriKey
    ) {
        try {
            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
            for (LiquorLikeDto dto: liquorLikeDtos.getContent()) {
                liquorTotalRes.add(
                        liquorDataService.getLiquorTotalData(
                                dto.getLiquorPriKey(),
                                userPriKey
                        )
                );
            }
            return new PageImpl<>(
                    liquorTotalRes,
                    liquorLikeDtos.getPageable(),
                    liquorLikeDtos.getTotalElements()
            );
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
