package com.sulsul.suldaksuldak.Service.liquor;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.bridge.BridgeDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.BridgeInterface;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorDelService {
    private final MtToLiRepository mtToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final SnToLiRepository snToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;

    /**
     * 재료와 술의 관계 제거
     */
    public Boolean deleteMtToLi(
            Long liquorPriKey,
            List<Long> tagPriKeys
    ) {
        try {
            for (Long tagPriKey: tagPriKeys) {
                Long bridgePriKey = findBridgeEntity(mtToLiRepository, liquorPriKey, tagPriKey);
                if (bridgePriKey != null) {
                    deleteLiquorTag(mtToLiRepository, bridgePriKey);
                }
            }
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 판매처와 술의 관계 제거
     */
    public Boolean deleteSlToLi(
            Long liquorPriKey,
            List<Long> tagPriKeys
    ) {
        try {
            for (Long tagPriKey: tagPriKeys) {
                Long bridgePriKey = findBridgeEntity(slToLiRepository, liquorPriKey, tagPriKey);
                if (bridgePriKey != null) {
                    deleteLiquorTag(slToLiRepository, bridgePriKey);
                }
            }
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 안주와 술의 관계 제거
     */
    public Boolean deleteSnToLi(
            Long liquorPriKey,
            List<Long> tagPriKeys
    ) {
        try {
            for (Long tagPriKey: tagPriKeys) {
                Long bridgePriKey = findBridgeEntity(snToLiRepository, liquorPriKey, tagPriKey);
                if (bridgePriKey != null) {
                    deleteLiquorTag(snToLiRepository, bridgePriKey);
                }
            }
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 상태와 술의 관계 제거
     */
    public Boolean deleteStToLi(
            Long liquorPriKey,
            List<Long> tagPriKeys
    ) {
        try {
            for (Long tagPriKey: tagPriKeys) {
                Long bridgePriKey = findBridgeEntity(stToLiRepository, liquorPriKey, tagPriKey);
                if (bridgePriKey != null) {
                    deleteLiquorTag(stToLiRepository, bridgePriKey);
                }
            }
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 맛과 술의 관계 제거
     */
    public Boolean deleteTtToLi(
            Long liquorPriKey,
            List<Long> tagPriKeys
    ) {
        try {
            for (Long tagPriKey: tagPriKeys) {
                Long bridgePriKey = findBridgeEntity(ttToLiRepository, liquorPriKey, tagPriKey);
                if (bridgePriKey != null) {
                    deleteLiquorTag(ttToLiRepository, bridgePriKey);
                }
            }
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    private <T extends BridgeInterface> Long findBridgeEntity(
            T repository,
            Long liquorPriKey,
            Long tagPriKey
    ) {
        try {
            Optional<BridgeDto> dto = repository.findByLiquorPriKeyAndTagPriKey(
                    liquorPriKey,
                    tagPriKey
            );
            return dto.map(BridgeDto::getId).orElse(null);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
    private <T extends JpaRepository<?, Long>> Boolean deleteLiquorTag(
            T repository,
            Long bridgePriKey
    ) {
        try {
            repository.deleteById(bridgePriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
