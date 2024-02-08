package com.sulsul.suldaksuldak.service.tag;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.tag.*;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.liquor.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.liquor.name.LiquorNameRepository;
import com.sulsul.suldaksuldak.repo.liquor.snack.LiquorSnackRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.repo.tag.material.LiquorMaterialRepository;
import com.sulsul.suldaksuldak.repo.tag.sell.LiquorSellRepository;
import com.sulsul.suldaksuldak.repo.tag.state.StateTypeRepository;
import com.sulsul.suldaksuldak.repo.tag.taste.TasteTypeRepository;
import com.sulsul.suldaksuldak.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final LiquorSnackRepository liquorSnackRepository;
    private final FileService fileService;

    /**
     * 주량 저장
     */
    public Boolean createDrinkingCapacity (
            Long id,
            String level,
            String color
    ) {
        try {
            if (id == null) {
                drinkingCapacityRepository.save(
                        DrinkingCapacity.of(null, level, color)
                );
            } else {
                drinkingCapacityRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    if (level != null)
                                        entity.setName(level);
                                    if (color != null)
                                        entity.setColor(color);
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
            String name,
            MultipartFile file
    ) {
        try {
            if (id == null) {
                FileBase fileBase = fileService.saveFile(file);
                liquorNameRepository.save(
                        LiquorName.of(null, name, fileBase)
                );
            } else {
                liquorNameRepository.findById(id)
                        .ifPresentOrElse(
                                entity -> {
                                    if (name != null)
                                        entity.setName(name);
                                    if (file != null) {
                                        FileBase fileBase = fileService.saveFile(file);
                                        if (entity.getFileBase() != null) {
                                            fileService.deleteFile(entity.getFileBase().getFileNm());
                                        }
                                        entity.setFileBase(fileBase);
                                    }
                                    liquorNameRepository.save(entity);
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND ENTITY");
                                }
                        );
            }
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(e.getErrorCode(), e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 추천 안주 생성 및 수정
     */
    public Boolean createLiquorSnack(
            LiquorSnackDto liquorSnackDto
    ) {
        try {
            if (liquorSnackDto.getId() == null) {
                liquorSnackRepository.save(liquorSnackDto.toEntity());
            } else {
                liquorSnackRepository.findById(liquorSnackDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    liquorSnackRepository.save(liquorSnackDto.updateEntity(findEntity));
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            ErrorMessage.NOT_FOUND_LIQUOR_DATA
                                    );
                                }
                        );
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}