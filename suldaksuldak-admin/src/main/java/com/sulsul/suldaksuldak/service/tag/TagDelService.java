package com.sulsul.suldaksuldak.service.tag;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagDelService {
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
        return deleteDrinkingCapacity(drinkingCapacityRepository, priKey);
    }

    public Boolean deleteLiquorAbv(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorAbvRepository, priKey);
    }

    public Boolean deleteLiquorDetail(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorDetailRepository, priKey);
    }

    public Boolean deleteLiquorMaterial(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorMaterialRepository, priKey);
    }

    public Boolean deleteLiquorName(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorNameRepository, priKey);
    }

    public Boolean deleteLiquorSell(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorSellRepository, priKey);
    }

    public Boolean deleteStateType(
            Long priKey
    ) {
        return deleteDrinkingCapacity(stateTypeRepository, priKey);
    }

    public Boolean deleteTasteType(
            Long priKey
    ) {
        return deleteDrinkingCapacity(tasteTypeRepository, priKey);
    }

    public Boolean deleteLiquorSnack(
            Long priKey
    ) {
        return deleteDrinkingCapacity(liquorSnackRepository, priKey);
    }

    private <T extends JpaRepository<?, Long>> Boolean deleteDrinkingCapacity(
            T repository,
            Long priKey
    ) {
        try {
            if (priKey == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND PRIKEY");
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
