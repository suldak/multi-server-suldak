package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.bridge.*;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.LiquorSnack;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import com.sulsul.suldaksuldak.domain.tag.LiquorSell;
import com.sulsul.suldaksuldak.domain.tag.StateType;
import com.sulsul.suldaksuldak.domain.tag.TasteType;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorTagService {
    private final LiquorRepository liquorRepository;
    // 레시피
    private final LiquorRecipeRepository liquorRecipeRepository;
    // 도수
    private final LiquorAbvRepository liquorAbvRepository;
    // 2차 분류
    private final LiquorDetailRepository liquorDetailRepository;
    // 주량
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    // 1차 분류
    private final LiquorNameRepository liquorNameRepository;

    // 추천 안주
    private final LiquorSnackRepository liquorSnackRepository;
    private final SnToLiRepository snToLiRepository;

    // 판매처
    private final LiquorSellRepository liquorSellRepository;
    private final SlToLiRepository slToLiRepository;

    // 재료
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final MtToLiRepository mtToLiRepository;

    // 상태
    private final StateTypeRepository stateTypeRepository;
    private final StToLiRepository stToLiRepository;

    // 맛
    private final TasteTypeRepository tasteTypeRepository;
    private final TtToLiRepository ttToLiRepository;

    private Liquor getLiquorDto(
            Long liquorPriKey
    ) {
        try {
            Optional<Liquor> liquor = liquorRepository.findById(liquorPriKey);
            if (liquor.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
            return liquor.get();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private void createLiquorToSnack(
            Liquor liquor,
            Long liquorSnackPriKey
    ) {
        try {
            Optional<LiquorSnack> liquorSnack = liquorSnackRepository.findById(liquorSnackPriKey);
            if (liquorSnack.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR SNACK DATA");

            Optional<BridgeDto> snToLiDto =
                    snToLiRepository
                            .findByLiquorPriKeyAndLiquorSnackPriKey(
                                    liquor.getId(),
                                    liquorSnackPriKey
                            );
            if (snToLiDto.isEmpty()) {
                snToLiRepository.save(
                        SnToLi.of(
                                null,
                                liquorSnack.get(),
                                liquor
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private void createLiquorToSell(
            Liquor liquor,
            Long liquorSellPriKey
    ) {
        try {
            Optional<LiquorSell> liquorSell = liquorSellRepository.findById(liquorSellPriKey);
            if (liquorSell.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR SELL DATA");

            Optional<BridgeDto> slToLiDto =
                    slToLiRepository
                            .findByLiquorPriKeyAndLiquorSellPriKey(
                                    liquor.getId(),
                                    liquorSellPriKey
                            );
            if (slToLiDto.isEmpty()) {
                slToLiRepository.save(
                        SlToLi.of(
                                null,
                                liquorSell.get(),
                                liquor
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private void createLiquorToMaterial(
            Liquor liquor,
            Long liquorMaterialPriKey
    ) {
        try {
            Optional<LiquorMaterial> liquorMaterial = liquorMaterialRepository.findById(liquorMaterialPriKey);
            if (liquorMaterial.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR MATERIAL DATA");

            Optional<BridgeDto> mtToLi =
                    mtToLiRepository.findByLiquorPriKeyAndLiquorMaterialPriKey(
                            liquor.getId(),
                            liquorMaterialPriKey
                    );
            if (mtToLi.isEmpty()) {
                mtToLiRepository.save(
                        MtToLi.of(
                                null,
                                liquorMaterial.get(),
                                liquor
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private void createLiquorToState(
            Liquor liquor,
            Long statePriKey
    ) {
        try {
            Optional<StateType> stateType = stateTypeRepository.findById(statePriKey);
            if (stateType.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND STATE TYPE DATA");

            Optional<BridgeDto> stToLi =
                    stToLiRepository.findByLiquorPriKeyAndStatePriKey(
                            liquor.getId(),
                            statePriKey
                    );
            if (stToLi.isEmpty()) {
                stToLiRepository.save(
                        StToLi.of(
                                null,
                                stateType.get(),
                                liquor
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private void createLiquorToTaste(
            Liquor liquor,
            Long tastePriKey
    ) {
        try {
            Optional<TasteType> tasteType = tasteTypeRepository.findById(tastePriKey);
            if (tasteType.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND TASTE TYPE DATA");

            Optional<BridgeDto> ttToLi =
                    ttToLiRepository.findByLiquorPriKeyAndTastePriKey(
                            liquor.getId(),
                            tastePriKey
                    );
            if (ttToLi.isEmpty()) {
                ttToLiRepository.save(
                        TtToLi.of(
                                null,
                                tasteType.get(),
                                liquor
                        )
                );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Boolean createLiquorTag(LiquorTotalReq liquorTotalReq) {
        try {
            Liquor liquor = getLiquorDto(liquorTotalReq.getId());
            if (liquorTotalReq.getSnackPriKeys() != null) {
                // 추천 안주 연결
                for (Long snackPriKey: liquorTotalReq.getSnackPriKeys()) {
                    createLiquorToSnack(liquor, snackPriKey);
                }
            }
            if (liquorTotalReq.getSellPriKeys() != null) {
                // 판매처 연결
                for (Long sellPriKey: liquorTotalReq.getSellPriKeys()) {
                    createLiquorToSell(liquor, sellPriKey);
                }
            }
            if (liquorTotalReq.getMaterialPriKeys() != null) {
                // 재료 연결
                for (Long materialPriKey: liquorTotalReq.getMaterialPriKeys()) {
                    createLiquorToMaterial(liquor, materialPriKey);
                }
            }
            if (liquorTotalReq.getStatePriKeys() != null) {
                // 기분 연결
                for (Long statePriKey: liquorTotalReq.getStatePriKeys()) {
                    createLiquorToState(liquor, statePriKey);
                }
            }
            if (liquorTotalReq.getTastePriKeys() != null) {
                // 맛 연결
                for (Long tastePriKey: liquorTotalReq.getTastePriKeys()) {
                    createLiquorToTaste(liquor, tastePriKey);
                }
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
