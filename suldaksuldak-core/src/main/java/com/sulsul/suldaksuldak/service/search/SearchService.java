package com.sulsul.suldaksuldak.service.search;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.search.SearchText;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.search.SearchTextDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.search.text.SearchTextRepository;
import com.sulsul.suldaksuldak.service.common.LiquorDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final UserRepository userRepository;
    private final LiquorDataService liquorDataService;
    private final SearchTextRepository searchTextRepository;

    public Boolean createSearchLog(
            Long userPriKey,
            LiquorTagSearchDto liquorTagSearchDto
    ) {
        try {
            List<String> searchTagTextList = new ArrayList<>();
            ExecutorService executor = Executors.newFixedThreadPool(9);
            CompletableFuture<List<LiquorAbvDto>> abvFList =
                    CompletableFuture.supplyAsync(() ->
                            liquorDataService
                                    .getLiquorAbvDtoList(
                                            liquorTagSearchDto.getLiquorAbvPriKeys()
                                    ),
                            executor
                    );
            CompletableFuture<List<LiquorDetailDto>> detailFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getLiquorDetailDtoList(
                                                    liquorTagSearchDto.getLiquorDetailPriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<LiquorNameDto>> nameFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getLiquorNameDtoList(
                                                    liquorTagSearchDto.getLiquorNamePriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<LiquorSnackDto>> snackFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getLiquorSnackDtoList(
                                                    liquorTagSearchDto.getSnackPriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<DrinkingCapacityDto>> drinkFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getDrinkingCapacityDtoList(
                                                    liquorTagSearchDto.getDrinkingCapacityPriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<LiquorMaterialDto>> materialFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getLiquorMaterialDtoList(
                                                    liquorTagSearchDto.getMaterialPriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<LiquorSellDto>> sellFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getLiquorSellDtoList(
                                                    liquorTagSearchDto.getSellPriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<StateTypeDto>> stateFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getStateTypeDtoList(
                                                    liquorTagSearchDto.getStatePriKeys()
                                            ),
                            executor
                    );
            CompletableFuture<List<TasteTypeDto>> tasteFList =
                    CompletableFuture.supplyAsync(() ->
                                    liquorDataService
                                            .getTasteTypeDtoList(
                                                    liquorTagSearchDto.getTastePriKeys()
                                            ),
                            executor
                    );
            searchTagTextList.addAll(abvFList.get().stream().map(LiquorAbvDto::getName).toList());
            searchTagTextList.addAll(detailFList.get().stream().map(LiquorDetailDto::getName).toList());
            searchTagTextList.addAll(nameFList.get().stream().map(LiquorNameDto::getName).toList());
            searchTagTextList.addAll(snackFList.get().stream().map(LiquorSnackDto::getName).toList());
            searchTagTextList.addAll(drinkFList.get().stream().map(DrinkingCapacityDto::getName).toList());
            searchTagTextList.addAll(materialFList.get().stream().map(LiquorMaterialDto::getName).toList());
            searchTagTextList.addAll(sellFList.get().stream().map(LiquorSellDto::getName).toList());
            searchTagTextList.addAll(stateFList.get().stream().map(StateTypeDto::getName).toList());
            searchTagTextList.addAll(tasteFList.get().stream().map(TasteTypeDto::getName).toList());

            Optional<User> user =
                    userPriKey == null ?
                            Optional.empty() :
                    userRepository.findById(userPriKey);
            if (!searchTagTextList.isEmpty()) {
                for (String name: searchTagTextList) {
                    searchTextRepository.save(
                            SearchText.of(
                                    null,
                                    true,
                                    name,
                                    user.orElse(null)
                            )
                    );
                }
            }
            if (liquorTagSearchDto.getSearchTag() != null
                    && !liquorTagSearchDto.getSearchTag().isBlank()
            ) {
                searchTextRepository.save(
                        SearchText.of(
                                null,
                                false,
                                liquorTagSearchDto.getSearchTag(),
                                user.orElse(null)
                        )
                );
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new GeneralException(e.getErrorCode(), e.getMessage());
            return false;
        }
    }

    public List<SearchTextDto> getSearchList(
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime,
            Long userPriKey
    ) {
        try {
            return searchTextRepository.findListByOption(
                    searchStartTime,
                    searchEndTime,
                    userPriKey
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
