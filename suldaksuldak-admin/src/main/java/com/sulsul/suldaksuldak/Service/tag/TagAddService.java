package com.sulsul.suldaksuldak.Service.tag;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.tag.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.tag.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.tag.material.LiquorMaterialRepository;
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
                        DrinkingCapacity.of(id, level)
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
                        LiquorAbv.of(id, name)
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
                        LiquorDetail.of(id, name)
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
                        LiquorMaterial.of(id, name)
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
}
