package com.sulsul.suldaksuldak.service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.bridge.*;
import com.sulsul.suldaksuldak.domain.liquor.Liquor;
import com.sulsul.suldaksuldak.domain.liquor.LiquorSnack;
import com.sulsul.suldaksuldak.domain.tag.*;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalReq;
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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorTagService {
    private final LiquorRepository liquorRepository;
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
//                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR SNACK DATA");
                return;

            Optional<BridgeDto> snToLiDto =
                    snToLiRepository
                            .findByLiquorPriKeyAndTagPriKey(
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
//                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR SELL DATA");
                return;

            Optional<BridgeDto> slToLiDto =
                    slToLiRepository
                            .findByLiquorPriKeyAndTagPriKey(
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
//                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND LIQUOR MATERIAL DATA");
                return;

            Optional<BridgeDto> mtToLi =
                    mtToLiRepository.findByLiquorPriKeyAndTagPriKey(
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
//                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND STATE TYPE DATA");
                return;

            Optional<BridgeDto> stToLi =
                    stToLiRepository.findByLiquorPriKeyAndTagPriKey(
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
//                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND TASTE TYPE DATA");
                return;

            Optional<BridgeDto> ttToLi =
                    ttToLiRepository.findByLiquorPriKeyAndTagPriKey(
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
            snToLiRepository.deleteByLiquorPriKey(liquor.getId());
            if (liquorTotalReq.getSnackPriKeys() != null) {
                // 추천 안주 연결
                for (Long snackPriKey: liquorTotalReq.getSnackPriKeys()) {
                    createLiquorToSnack(liquor, snackPriKey);
                }
            }
            slToLiRepository.deleteByLiquorPriKey(liquor.getId());
            if (liquorTotalReq.getSellPriKeys() != null) {
                // 판매처 연결
                for (Long sellPriKey: liquorTotalReq.getSellPriKeys()) {
                    createLiquorToSell(liquor, sellPriKey);
                }
            }
            mtToLiRepository.deleteByLiquorPriKey(liquor.getId());
            if (liquorTotalReq.getMaterialPriKeys() != null) {
                // 재료 연결
                for (Long materialPriKey: liquorTotalReq.getMaterialPriKeys()) {
                    createLiquorToMaterial(liquor, materialPriKey);
                }
            }
            stToLiRepository.deleteByLiquorPriKey(liquor.getId());
            if (liquorTotalReq.getStatePriKeys() != null) {
                // 기분 연결
                for (Long statePriKey: liquorTotalReq.getStatePriKeys()) {
                    createLiquorToState(liquor, statePriKey);
                }
            }
            ttToLiRepository.deleteByLiquorPriKey(liquor.getId());
            if (liquorTotalReq.getTastePriKeys() != null) {
                // 맛 연결
                for (Long tastePriKey: liquorTotalReq.getTastePriKeys()) {
                    createLiquorToTaste(liquor, tastePriKey);
                }
            }
//            Optional<LiquorAbv> liquorAbv = Optional.empty();
//            Optional<LiquorDetail> liquorDetail = Optional.empty();
//            Optional<DrinkingCapacity> drinkingCapacity = Optional.empty();
//            Optional<LiquorName> liquorName = Optional.empty();
//            if (liquorTotalReq.getLiquorAbvId() != null) {
//                liquorAbv = liquorAbvRepository.findById(liquorTotalReq.getLiquorAbvId());
//            }
//            if (liquorTotalReq.getLiquorDetailId() != null) {
//                liquorDetail = liquorDetailRepository.findById(liquorTotalReq.getLiquorDetailId());
//            }
//            if (liquorTotalReq.getDrinkingCapacityId() != null) {
//                drinkingCapacity = drinkingCapacityRepository.findById(liquorTotalReq.getDrinkingCapacityId());
//            }
//            if (liquorTotalReq.getLiquorNameId() != null) {
//                liquorName = liquorNameRepository.findById(liquorTotalReq.getLiquorNameId());
//            }
//            liquorRepository.save(
//                    LiquorDto.of(liquor)
//                            .updateEntity(
//                                    liquor,
//                                    liquorAbv.orElse(null),
//                                    liquorDetail.orElse(null),
//                                    drinkingCapacity.orElse(null),
//                                    liquorName.orElse(null)
//                            )
//            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
