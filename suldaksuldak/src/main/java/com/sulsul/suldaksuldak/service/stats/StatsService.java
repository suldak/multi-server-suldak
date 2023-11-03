package com.sulsul.suldaksuldak.service.stats;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.stats.TagType;
import com.sulsul.suldaksuldak.domain.stats.LiquorSearchLog;
import com.sulsul.suldaksuldak.domain.user.User;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTagSearchDto;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorTotalRes;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorDto;
import com.sulsul.suldaksuldak.dto.stats.user.UserLiquorTagDto;
import com.sulsul.suldaksuldak.dto.stats.user.UserTagDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.repo.auth.UserRepository;
import com.sulsul.suldaksuldak.repo.liquor.liquor.LiquorRepository;
import com.sulsul.suldaksuldak.repo.stats.search.LiquorSearchLogRepository;
import com.sulsul.suldaksuldak.repo.stats.user.UserLiquorRepository;
import com.sulsul.suldaksuldak.repo.stats.user.UserTagRepository;
import com.sulsul.suldaksuldak.service.common.LiquorDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;
    private final UserLiquorRepository userLiquorRepository;
    private final LiquorSearchLogRepository liquorSearchLogRepository;
    private final UserTagRepository userTagRepository;
    private final LiquorDataService liquorDataService;

    /**
     * 유저 - 술 집게 Table에 집계
     */
    public Boolean countSearchCnt(
            Long userId,
            Long liquorId
    ) {
        try {
            Optional<UserLiquorDto> dto = userLiquorRepository.findByUserPriKeyAndLiquorPriKey(userId, liquorId);
            if (dto.isEmpty()) {
                userRepository.findById(userId)
                        .ifPresent(
                                findUser -> {
                                    liquorRepository.findById(liquorId)
                                            .ifPresent(
                                                    findLiquor -> {
                                                        userLiquorRepository.save(
                                                                UserLiquorDto
                                                                        .of(null, userId, liquorId, 0.1)
                                                                        .toEntity(findUser, findLiquor)
                                                        );
                                                    }
                                            );
                                }
                        );
            } else {
                userLiquorRepository.findById(dto.get().getId())
                        .ifPresentOrElse(
                                findEntity -> {
                                    userLiquorRepository.save(
                                            UserLiquorDto.addSearchCnt(findEntity)
                                    );
                                },
                                () -> {
                                    throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND DATA");
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

    /**
     * 술 검색 집계 추가
     */
    public Boolean createLiquorSearchLog(
            Long liquorPriKey
    ) {
        try {
            if (liquorPriKey == null) throw new GeneralException(ErrorCode.BAD_REQUEST, "Liquor Key is Null");
            String priKey = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
                    + "_" + liquorPriKey;
            liquorRepository.findById(liquorPriKey)
                    .ifPresent(
                            findLiquor -> {
                                liquorSearchLogRepository.save(
                                        LiquorSearchLog.of(
                                                priKey,
                                                findLiquor
                                        )
                                );
                            }
                    );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }

    /**
     * 집계된 술 통계를 기준으로 기간 별 조회
     */
    public Page<Long> getLiquorDataByLogStats(
            LocalDateTime startAt,
            LocalDateTime endAt,
            Pageable pageable
    ) {
        try {
            return liquorSearchLogRepository.findLiquorPriKeyByDateRange(
                    pageable,
                    startAt,
                    endAt
            );
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 유저 별 추천 술 목록 조회
     */
    public List<UserLiquorTagDto> getLiquorPriKeyByUserStats(
            Long userPriKey,
            Integer limitNum
    ) {
        try {
            return userLiquorRepository.findRatingByUserId(userPriKey, limitNum);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Weight
     */
    public LiquorTagSearchDto getTagListByUserPriKey(
            Long userPriKey,
            Integer limitNum,
            Integer pageNum,
            Integer recordSize
    ) {
        try {
            List<UserTagDto> userTagDtos = userTagRepository.findByUserPriKey(
                    userPriKey,
                    limitNum
            );

            LiquorTagSearchDto resultDto = LiquorTagSearchDto.emptyListOf();
            for (UserTagDto dto: userTagDtos) {
                if (dto.getTagType().equals(TagType.DRINKING_CAPACITY)) {
                    resultDto.getDrinkingCapacityPriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_ABV)) {
                    resultDto.getLiquorAbvPriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_DETAIL)) {
                    resultDto.getLiquorDetailPriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_MATERIAL)) {
                    resultDto.getMaterialPriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_NAME)) {
                    resultDto.getLiquorNamePriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_SELL)) {
                    resultDto.getSellPriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.STATE_TYPE)) {
                    resultDto.getStatePriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.TASTE_TYPE)) {
                    resultDto.getTastePriKeys().add(dto.getTagId());
                } else if (dto.getTagType().equals(TagType.LIQUOR_SNACK)) {
                    resultDto.getSnackPriKeys().add(dto.getTagId());
                }
            }
            resultDto.setPageNum(pageNum);
            resultDto.setRecordSize(recordSize);
            return resultDto;
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * UserTag 저장 및 수정
     */
    public Boolean countTagCnt(
            User user,
            TagType tagType,
            Long tagId,
            Double weight
    ) {
        try {
            Long userPriKey = user.getId();
            Optional<UserTagDto> userTagDto =
                    userTagRepository.findByUserPriKeyAndTagTypeAndTagId(
                            userPriKey,
                            tagType,
                            tagId
                    );
            if (userTagDto.isEmpty()) {
                String priKey = tagType + "_" + userPriKey + "_" + tagId;
                userTagRepository.save(
                        UserTagDto.of(
                                priKey,
                                tagType,
                                tagId,
                                weight,
                                userPriKey
                        ).toEntity(user)
                );
            } else {
                userTagRepository.findById(userTagDto.get().getId())
                        .ifPresent(
                                findEntity -> {
                                    userTagRepository.save(
                                            UserTagDto.updateWeight(
                                                    findEntity,
                                                    weight
                                            )
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

    /**
     * 술에 등록된 태그들을 유저의 집계 테이블에 추가
     */
    public Boolean countSearchTagCnt(
            Long userPriKey,
            Long liquorPriKey
    ) {
        try {
            Optional<User> user = userRepository.findById(userPriKey);
            if (user.isEmpty())
                throw new GeneralException(ErrorCode.NOT_FOUND, "NOT FOUND USER");
            return countSearchTagCnt(user.get(), liquorPriKey);
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /**
     * 술에 등록된 태그들을 유저의 집계 테이블에 추가
     */
    public Boolean countSearchTagCnt(
            User user,
            Long liquorPriKey
    ) {
        try {
            Long userPriKey = user.getId();
            LiquorTotalRes liquorTotalRes = liquorDataService.getLiquorTotalData(liquorPriKey);
            HashMap<TagType, List<Long>> tagMap = new HashMap<>();
            LiquorAbvDto liquorAbvDto = liquorTotalRes.getLiquorAbvDto();
            if (liquorAbvDto != null) tagMap.put(TagType.LIQUOR_ABV, List.of(liquorAbvDto.getId()));
            LiquorDetailDto liquorDetailDto = liquorTotalRes.getLiquorDetailDto();
            if (liquorDetailDto != null) tagMap.put(TagType.LIQUOR_DETAIL, List.of(liquorDetailDto.getId()));
            DrinkingCapacityDto drinkingCapacityDto = liquorTotalRes.getDrinkingCapacityDto();
            if (drinkingCapacityDto != null) tagMap.put(TagType.DRINKING_CAPACITY, List.of(drinkingCapacityDto.getId()));
            LiquorNameDto liquorNameDto = liquorTotalRes.getLiquorNameDto();
            if (liquorNameDto != null) tagMap.put(TagType.LIQUOR_NAME, List.of(liquorNameDto.getId()));
            List<LiquorSnackRes> liquorSnackRes = liquorTotalRes.getLiquorSnackRes();
            if (liquorSnackRes != null && !liquorSnackRes.isEmpty())
                tagMap.put(TagType.LIQUOR_SNACK,
                        liquorSnackRes.stream().map(LiquorSnackRes::getId).collect(Collectors.toList()));
            List<LiquorSellDto> liquorSellDtos = liquorTotalRes.getLiquorSellDtos();
            if (liquorSellDtos != null && !liquorSellDtos.isEmpty())
                tagMap.put(TagType.LIQUOR_SELL,
                        liquorSellDtos.stream().map(LiquorSellDto::getId).collect(Collectors.toList()));
            List<LiquorMaterialDto> liquorMaterialDtos = liquorTotalRes.getLiquorMaterialDtos();
            if (liquorMaterialDtos != null && !liquorMaterialDtos.isEmpty())
                tagMap.put(TagType.LIQUOR_MATERIAL,
                        liquorMaterialDtos.stream().map(LiquorMaterialDto::getId).collect(Collectors.toList()));
            List<StateTypeDto> stateTypeDtos = liquorTotalRes.getStateTypeDtos();
            if (stateTypeDtos != null && !stateTypeDtos.isEmpty())
                tagMap.put(TagType.STATE_TYPE,
                        stateTypeDtos.stream().map(StateTypeDto::getId).collect(Collectors.toList()));
            List<TasteTypeDto> tasteTypeDtos = liquorTotalRes.getTasteTypeDtos();
            if (tasteTypeDtos != null && !tasteTypeDtos.isEmpty())
                tagMap.put(TagType.TASTE_TYPE,
                        tasteTypeDtos.stream().map(TasteTypeDto::getId).collect(Collectors.toList()));

            for (TagType tagType: tagMap.keySet()) {
                for (Long tagId: tagMap.get(tagType)) {
                    countTagCnt(
                            user,
                            tagType,
                            tagId,
                            0.1
                    );
                }
            }
        } catch (GeneralException e) {
            throw new GeneralException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
        return true;
    }
}
