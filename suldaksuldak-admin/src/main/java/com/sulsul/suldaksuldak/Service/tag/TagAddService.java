package com.sulsul.suldaksuldak.Service.tag;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TagAddService {
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final LiquorNameRepository liquorNameRepository;
    private final LiquorSellRepository liquorSellRepository;
    private final StateTypeRepository stateTypeRepository;
    private final TasteTypeRepository tasteTypeRepository;

    /**
     * 주량 저장
     */
    public Boolean createDrinkingCapacity (
            Long id,
            String level
    ) {
        try {
            if (id == null) {
                drinkingCapacityRepository.save(
                        DrinkingCapacity.of(null, level)
                );
            } else {
                drinkingCapacityRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setLevel(level);
                                    drinkingCapacityRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 도수 저장
     */
    public Boolean createLiquorAbv (
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                liquorAbvRepository.save(
                        LiquorAbv.of(null, name)
                );
            } else {
                liquorAbvRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    liquorAbvRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 주종 저장
     */
    public Boolean createLiquorDetail(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                liquorDetailRepository.save(
                        LiquorDetail.of(null, name)
                );
            } else {
                liquorDetailRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    liquorDetailRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 주종 저장
     */
    public Boolean createLiquorMaterial(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                liquorMaterialRepository.save(
                        LiquorMaterial.of(null, name)
                );
            } else {
                liquorMaterialRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    liquorMaterialRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 1차 분류 저장
     */
    public Boolean createLiquorName(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                liquorNameRepository.save(
                        LiquorName.of(null, name)
                );
            } else {
                liquorNameRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    liquorNameRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 판매처 저장
     */
    public Boolean createLiquorSell(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                liquorSellRepository.save(
                        LiquorSell.of(null, name)
                );
            } else {
                liquorSellRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    liquorSellRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 상태 저장
     */
    public Boolean createStateType(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                stateTypeRepository.save(
                        StateType.of(null, name)
                );
            } else {
                stateTypeRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    stateTypeRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 상태 저장
     */
    public Boolean createTasteType(
            Long id,
            String name
    ) {
        try {
            if (id == null) {
                tasteTypeRepository.save(
                        TasteType.of(null, name)
                );
            } else {
                tasteTypeRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.setName(name);
                                    tasteTypeRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
