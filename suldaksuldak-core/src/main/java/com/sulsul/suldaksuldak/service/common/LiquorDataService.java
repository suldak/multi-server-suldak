package com.sulsul.suldaksuldak.service.common;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.dto.liquor.like.LiquorLikeDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.liquor.abv.LiquorAbvRepository;
import com.sulsul.suldaksuldak.repo.liquor.detail.LiquorDetailRepository;
import com.sulsul.suldaksuldak.repo.liquor.like.LiquorLikeRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquorDataService {
    private final LiquorRepository liquorRepository;
    private final LiquorAbvRepository liquorAbvRepository;
    private final LiquorDetailRepository liquorDetailRepository;
    private final DrinkingCapacityRepository drinkingCapacityRepository;
    private final LiquorNameRepository liquorNameRepository;
    private final LiquorSnackRepository liquorSnackRepository;
    private final LiquorSellRepository liquorSellRepository;
    private final LiquorMaterialRepository liquorMaterialRepository;
    private final StateTypeRepository stateTypeRepository;
    private final TasteTypeRepository tasteTypeRepository;
    private final LiquorLikeRepository liquorLikeRepository;

    /**
     * 유저가 해당 술에 즐겨찾기를 등록했는지 여부
     */
    public Optional<LiquorLike> getLiquorLikeUser(
            Long userPriKey,
            Long liquorPriKey
    ) {
        try {
            if (userPriKey == null)
                return Optional.empty();
            return liquorLikeRepository.findByByUserPriKeyAndLiquorPriKey(
                    userPriKey,
                    liquorPriKey
            );
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(
                    ErrorCode.DATA_ACCESS_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 삭제 처리된 술은 조회하지 않음
     */
    public Page<LiquorLikeDto> getLiquorLikeList(
            Long liquorPriKey,
            Long userPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    ) {
        try {
            return liquorLikeRepository.findByLiquorPriKeyWithUserPriKey(
                    liquorPriKey,
                    userPriKey,
                    startAt,
                    endAt,
                    pageable
            );
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

    /**
     * 술에 관련된 모든 데이터 조회
     * 삭제된 술을 조회하면 오류 발생
     */
    public LiquorTotalRes getLiquorTotalData(
            Long liquorPriKey,
            Long userPriKey
    ) {
        try {
            if (liquorPriKey == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST, "NOT FOUND LIQUOR PRI KEY");
            }
            // 술
            Optional<LiquorDto> liquorDto = liquorRepository.findByPriKey(liquorPriKey);
            if (liquorDto.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, ErrorMessage.NOT_FOUND_LIQUOR_DATA);
            return getLiquorTotalData(
                    liquorDto.get(),
                    userPriKey
            );
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    public LiquorTotalRes getLiquorTotalData(
            LiquorDto liquorDto,
            Long userPriKey
    ) {
        try {
            long startTime = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(10);

            CompletableFuture<Optional<LiquorAbvDto>> liquorAbvCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorAbvDto(liquorDto.getLiquorAbvId()), executor
                    );

            CompletableFuture<Optional<LiquorDetailDto>> liquorDetailCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorDetailDto(liquorDto.getLiquorDetailId()), executor
                    );

            CompletableFuture<Optional<DrinkingCapacityDto>> liquorDrinkCpCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getDrinkingCapacityDto(liquorDto.getDrinkingCapacityId()), executor
                    );

            CompletableFuture<Optional<LiquorNameDto>> liquorNameCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorNameDto(liquorDto.getLiquorNameId()), executor
                    );

            CompletableFuture<List<LiquorSnackDto>> liquorSnackCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorSnackDtoList(liquorDto.getId()), executor
                    );

            CompletableFuture<List<LiquorSellDto>> liquorSellCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorSellDtoList(liquorDto.getId()), executor
                    );

            CompletableFuture<List<LiquorMaterialDto>> liquorMtCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorMaterialDtoList(liquorDto.getId()), executor
                    );

            CompletableFuture<List<StateTypeDto>> liquorStateCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getStateTypeDtoList(liquorDto.getId()), executor
                    );

            CompletableFuture<List<TasteTypeDto>> liquorTasteCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getTasteTypeDtoList(liquorDto.getId()), executor
                    );
            CompletableFuture<Optional<LiquorLike>> liquorLikeCompletableFuture =
                    CompletableFuture.supplyAsync(() ->
                            getLiquorLikeUser(userPriKey, liquorDto.getId()), executor
                    );

            CompletableFuture<Void> allOf = CompletableFuture.allOf(
                    liquorAbvCompletableFuture,
                    liquorDetailCompletableFuture,
                    liquorDrinkCpCompletableFuture,
                    liquorNameCompletableFuture,
                    liquorSnackCompletableFuture,
                    liquorSellCompletableFuture,
                    liquorMtCompletableFuture,
                    liquorStateCompletableFuture,
                    liquorTasteCompletableFuture,
                    liquorLikeCompletableFuture
            );

            allOf.get();

            Optional<LiquorAbvDto> liquorAbvDto = liquorAbvCompletableFuture.get();
            Optional<LiquorDetailDto> liquorDetailDto = liquorDetailCompletableFuture.get();
            Optional<DrinkingCapacityDto> drinkingCapacityDto = liquorDrinkCpCompletableFuture.get();
            Optional<LiquorNameDto> liquorNameDto = liquorNameCompletableFuture.get();
            List<LiquorSnackDto> liquorSnackDtos = liquorSnackCompletableFuture.get();
            List<LiquorSellDto> liquorSellDtos = liquorSellCompletableFuture.get();
            List<LiquorMaterialDto> liquorMaterialDtos = liquorMtCompletableFuture.get();
            List<StateTypeDto> stateTypeDtos = liquorStateCompletableFuture.get();
            List<TasteTypeDto> tasteTypeDtos = liquorTasteCompletableFuture.get();
            Optional<LiquorLike> liquorLike = liquorLikeCompletableFuture.get();

            log.info("[{} 조회] >> {}", liquorDto.getName(), (System.currentTimeMillis() - startTime) / 1000.0);
            return LiquorTotalRes.of(
                    liquorDto,
                    liquorLike,
                    liquorAbvDto,
                    liquorDetailDto,
                    drinkingCapacityDto,
                    liquorNameDto,
                    liquorSnackDtos,
                    liquorSellDtos,
                    liquorMaterialDtos,
                    stateTypeDtos,
                    tasteTypeDtos
            );
        } catch (GeneralException e) {
            e.printStackTrace();
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 도수 정보 가져오기
     */
    public Optional<LiquorAbvDto> getLiquorAbvDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorAbvRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 2차 분류 종류 가져오기
     */
    public Optional<LiquorDetailDto> getLiquorDetailDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorDetailRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 주량 정보 가져오기
     */
    public Optional<DrinkingCapacityDto> getDrinkingCapacityDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return drinkingCapacityRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키로 1차 분류 정보 가져오기
     */
    public Optional<LiquorNameDto> getLiquorNameDto(Long priKey) {
        try {
            if (priKey == null) return Optional.empty();
            return liquorNameRepository.findByPriKey(priKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 안주 태그 목록 (RES) 가져오기
     */
    public List<LiquorSnackRes> getLiquorSnackResList(
            Long liquorPriKey
    ) {
        try {
            return liquorSnackRepository
                    .findByLiquorPriKey(liquorPriKey).stream().map(LiquorSnackRes::from).toList();
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 안주 태그 목록 (DTO) 가져오기
     */
    public List<LiquorSnackDto> getLiquorSnackDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorSnackRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 판매처 태그 목록 가져오기
     */
    public List<LiquorSellDto> getLiquorSellDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorSellRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 재료 태그 목록 가져오기
     */
    public List<LiquorMaterialDto> getLiquorMaterialDtoList(
            Long liquorPriKey
    ) {
        try {
            return liquorMaterialRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 상태 태그 목록 가져오기
     */
    public List<StateTypeDto> getStateTypeDtoList(
            Long liquorPriKey
    ) {
        try {
            return stateTypeRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술 기본키로 해당하는 맛 태그 목록 가져오기
     */
    public List<TasteTypeDto> getTasteTypeDtoList(
            Long liquorPriKey
    ) {
        try {
            return tasteTypeRepository.findByLiquorPriKey(liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 기본키 리스틀 이용해서 도수 목록 조회
     */
    public List<LiquorAbvDto> getLiquorAbvDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorAbvRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 2차 분류 목록 조회
     */
    public List<LiquorDetailDto> getLiquorDetailDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorDetailRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 1차 분류 목록 조회
     */
    public List<LiquorNameDto> getLiquorNameDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorNameRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 추천 안주 목록 조회
     */
    public List<LiquorSnackDto> getLiquorSnackDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorSnackRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 주량 정보 목록 조회
     */
    public List<DrinkingCapacityDto> getDrinkingCapacityDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return drinkingCapacityRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 재료 목록 조회
     */
    public List<LiquorMaterialDto> getLiquorMaterialDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorMaterialRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 판매처 목록 조회
     */
    public List<LiquorSellDto> getLiquorSellDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return liquorSellRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 맛 정보 목록 조회
     */
    public List<StateTypeDto> getStateTypeDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return stateTypeRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }

    /**
     * 기본키 리스틀 이용해서 상태 정보 목록 조회
     */
    public List<TasteTypeDto> getTasteTypeDtoList(
            List<Long> priKeyList
    ) {
        try {
            if (priKeyList == null || priKeyList.isEmpty())
                return List.of();
            return tasteTypeRepository.findByPriKeyList(priKeyList);
        } catch (GeneralException e) {
            throw new GeneralException(
                    e.getErrorCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            throw new GeneralException(
                    ErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}
