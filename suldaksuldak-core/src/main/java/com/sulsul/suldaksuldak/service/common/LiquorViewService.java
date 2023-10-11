package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.bridge.mt.MtToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.sell.SlToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.snack.SnToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.st.StToLiRepository;
import com.sulsul.suldaksuldak.repo.bridge.tt.TtToLiRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.tool.UtilTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorViewService {
    private final LiquorDataService liquorDataService;
    private final LiquorRepository liquorRepository;
    private final SnToLiRepository snToLiRepository;
    private final SlToLiRepository slToLiRepository;
    private final StToLiRepository stToLiRepository;
    private final TtToLiRepository ttToLiRepository;
    private final MtToLiRepository mtToLiRepository;

    public Page<LiquorTotalRes> getLiquorByTag(
            LiquorTagSearchDto liquorTagSearchDto,
            Pageable pageable
    ) {
        try {
            List<Long> resultLiquorPriKey = liquorRepository.findAllLiquorPriKey();
            if (liquorTagSearchDto.getSearchTag() != null && !liquorTagSearchDto.getSearchTag().isBlank()) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findBySearchTag(liquorTagSearchDto.getSearchTag())
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorAbvPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorAbvPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorAbvPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorDetailPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorDetailPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorDetailPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getDrinkingCapacityPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByDrinkingCapacityPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getDrinkingCapacityPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getLiquorNamePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        liquorRepository.findByLiquorNamePriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getLiquorNamePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSnackPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        snToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getSnackPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getSellPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        slToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getSellPriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getStatePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        stToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getStatePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getTastePriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        ttToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getTastePriKeys()
                        )
                );
            }
            if (UtilTool.checkLongList(liquorTagSearchDto.getMaterialPriKeys())) {
                resultLiquorPriKey = UtilTool.findOverlappingElements(
                        resultLiquorPriKey,
                        mtToLiRepository.findLiquorPriKeyByTagPriKey(
                                resultLiquorPriKey,
                                liquorTagSearchDto.getMaterialPriKeys()
                        )
                );
            }
            resultLiquorPriKey = UtilTool.removeDuplicates(resultLiquorPriKey);
            log.info(resultLiquorPriKey.toString());
            Page<LiquorDto> liquorDto = liquorRepository.findByLiquorPriKeyListAndSearchTag(
                    pageable,
                    resultLiquorPriKey
            );
            List<LiquorTotalRes> liquorTotalRes = new ArrayList<>();
            for (LiquorDto res: liquorDto.getContent()) {
                liquorTotalRes.add(liquorDataService.getLiquorTotalData(res));
            }
            return new PageImpl<>(
                    liquorTotalRes,
                    liquorDto.getPageable(),
                    liquorDto.getTotalElements()
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public Page<LiquorTotalRes> getLatestLiquor(
            Pageable pageable
    ) {
        try {
            Page<Long> liquorDtos = liquorRepository.findByCreatedLatest(pageable);
            List<LiquorTotalRes> resultContent = new ArrayList<>();
            for (Long liquorPriKey: liquorDtos.getContent()) {
                resultContent.add(liquorDataService.getLiquorTotalData(liquorPriKey));
            }
            return new PageImpl<>(
                    resultContent,
                    liquorDtos.getPageable(),
                    liquorDtos.getTotalElements()
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
