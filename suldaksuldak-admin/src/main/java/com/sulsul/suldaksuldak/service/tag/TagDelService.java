package com.sulsul.suldaksuldak.service.tag;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.domain.tag.LiquorMaterial;
import com.sulsul.suldaksuldak.domain.tag.LiquorName;
import com.sulsul.suldaksuldak.domain.tag.LiquorSnack;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagDelService {
    private final FileService fileService;
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final LiquorNameRepository liquorNameRepository;
    private final LiquorSellRepository liquorSellRepository;
    private final StateTypeRepository stateTypeRepository;
    private final TasteTypeRepository tasteTypeRepository;
    private final LiquorSnackRepository liquorSnackRepository;

    public Boolean deleteDrinkingCapacity(
            Long priKey
    ) {
        return deleteTagData(drinkingCapacityRepository, priKey);
    }

    public Boolean deleteLiquorAbv(
            Long priKey
    ) {
        return deleteTagData(liquorAbvRepository, priKey);
    }

    public Boolean deleteLiquorDetail(
            Long priKey
    ) {
        return deleteTagData(liquorDetailRepository, priKey);
    }

    public Boolean deleteLiquorMaterial(
            Long priKey
    ) {
//        return deleteDrinkingCapacity(liquorMaterialRepository, priKey);
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키 정보가 누락되었습니다."
                );
            Optional<LiquorMaterial> liquorMaterial =
                    liquorMaterialRepository.findById(priKey);
            if (liquorMaterial.isEmpty())
                return true;
            deleteTagData(liquorMaterialRepository, priKey);
            fileService.deleteFile(liquorMaterial.get().getFileBase().getFileNm());
            return true;
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

    public Boolean deleteLiquorName(
            Long priKey
    ) {
//        return deleteDrinkingCapacity(liquorNameRepository, priKey);
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키 정보가 누락되었습니다."
                );
            Optional<LiquorName> liquorName =
                    liquorNameRepository.findById(priKey);
            if (liquorName.isEmpty())
                return true;
            deleteTagData(liquorNameRepository, priKey);
            fileService.deleteFile(liquorName.get().getFileBase().getFileNm());
            return true;
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

    public Boolean deleteLiquorSell(
            Long priKey
    ) {
        return deleteTagData(liquorSellRepository, priKey);
    }

    public Boolean deleteStateType(
            Long priKey
    ) {
        return deleteTagData(stateTypeRepository, priKey);
    }

    public Boolean deleteTasteType(
            Long priKey
    ) {
        return deleteTagData(tasteTypeRepository, priKey);
    }

    public Boolean deleteLiquorSnack(
            Long priKey
    ) {
//        return deleteDrinkingCapacity(liquorSnackRepository, priKey);
        try {
            if (priKey == null)
                throw new GeneralException(
                        ErrorCode.BAD_REQUEST,
                        "기본키 정보가 누락되었습니다."
                );
            Optional<LiquorSnack> liquorSnack =
                    liquorSnackRepository.findById(priKey);
            if (liquorSnack.isEmpty())
                return true;
            deleteTagData(liquorSnackRepository, priKey);
            fileService.deleteFile(liquorSnack.get().getFileBase().getFileNm());
            return true;
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

    private <T extends JpaRepository<?, Long>> Boolean deleteTagData(
            T repository,
            Long priKey
    ) {
        try {
            if (priKey == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "기본키 정보가 누락되었습니다.");
            }
            repository.deleteById(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
