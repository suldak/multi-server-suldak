package com.sulsul.suldaksuldak.service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.file.FileBase;
import com.sulsul.suldaksuldak.domain.tag.DrinkingCapacity;
import com.sulsul.suldaksuldak.domain.tag.LiquorAbv;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.liquor.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.liquor.name.LiquorNameRepository;
import com.sulsul.suldaksuldak.repo.tag.capacity.DrinkingCapacityRepository;
import com.sulsul.suldaksuldak.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorAddService {
    private final FileService fileService;
    private final LiquorRepository liquorRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorNameRepository liquorNameRepository;

    /**
     * 술 생성 및 수정
     */
//    public Boolean createLiquor(
    public Long createLiquor(
            LiquorDto liquorDto,
            MultipartFile file
    ) {
        try {
            LiquorAbv liquorAbv;
            if (liquorDto.getLiquorAbvId() != null) {
                liquorAbv = liquorAbvRepository.findById(liquorDto.getLiquorAbvId())
                        .orElse(null);
            } else {
                liquorAbv = null;
            }
            LiquorDetail liquorDetail;
            if (liquorDto.getLiquorDetailId() != null) {
                liquorDetail = liquorDetailRepository.findById(liquorDto.getLiquorDetailId())
                        .orElse(null);
            } else {
                liquorDetail = null;
            }
            DrinkingCapacity drinkingCapacity;
            if (liquorDto.getDrinkingCapacityId() != null) {
                drinkingCapacity = drinkingCapacityRepository.findById(liquorDto.getDrinkingCapacityId())
                        .orElse(null);
            } else {
                drinkingCapacity = null;
            }
            LiquorName liquorName;
            if (liquorDto.getLiquorNameId() != null) {
                liquorName = liquorNameRepository.findById(liquorDto.getLiquorNameId()).orElse(null);
            } else {
                liquorName = null;
            }
            if (liquorDto.getId() == null) {
                // 신규 생성
                FileBase fileBase = fileService.saveFile(file);
                return liquorRepository
                        .save(liquorDto.toEntity(liquorAbv, liquorDetail, drinkingCapacity, liquorName, fileBase))
                        .getId();
            } else {
                liquorRepository.findById(liquorDto.getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    if (file == null) {
                                        liquorRepository.save(
                                                liquorDto.updateEntity(findEntity, liquorAbv, liquorDetail, drinkingCapacity, liquorName)
                                        );
                                    } else {
                                        FileBase oriFileBase = findEntity.getFileBase();
                                        FileBase fileBase = fileService.saveFile(file);
                                        liquorRepository.save(
                                                liquorDto.updateEntity(findEntity, liquorAbv, liquorDetail, drinkingCapacity, liquorName, fileBase)
                                        );
                                        if (oriFileBase != null)
                                            fileService.deleteFile(oriFileBase.getFileNm());
                                    }
                                },
                                () -> {
                                    throw new GeneralException(
                                            ErrorCode.NOT_FOUND,
                                            ErrorMessage.NOT_FOUND_LIQUOR_DATA
                                    );
                                }
                        );
                return liquorDto.getId();
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술의 레시피 생성 또는 수젛
     */
    public Boolean putLiquorRecipe(
            Long id,
            String liquorRecipe
    ) {
        try {
            if (id == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "Liquor PriKey Is Null");
            liquorRepository.findById(id)
                    .ifPresentOrElse(
                            findLiquor -> {
                                liquorRepository.save(
                                        LiquorDto.updateLiquorRecipe(findLiquor, liquorRecipe)
                                );
                            },
                            () -> {
                                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
                            }
                    );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
