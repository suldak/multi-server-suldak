package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.constant.file.FileUrl;
import com.sulsul.suldaksuldak.domain.liquor.LiquorLike;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackDto;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@ApiModel(value = "술에 관련된 모든 정보")
public class LiquorTotalRes {
    @ApiModelProperty(value = "술 기본키")
    Long id;
    @ApiModelProperty(value = "술 이름")
    String name;
    @ApiModelProperty(value = "술 요약 설명")
    String summaryExplanation;
    @ApiModelProperty(value = "술 상세 설명")
    String detailExplanation;
    @ApiModelProperty(value = "술의 레시피")
    String liquorRecipe;
    @ApiModelProperty(value = "술의 정확한 도수")
    Double detailAbv;
    @ApiModelProperty(value = "술 사진 URL")
    String liquorPictureUrl;
    // 도수
    LiquorAbvDto liquorAbvDto;
    // 2차 분류
    LiquorDetailDto liquorDetailDto;
    // 주량
    DrinkingCapacityDto drinkingCapacityDto;
    // 1차 분루
    LiquorNameDto liquorNameDto;

    // 추천 안주
    List<LiquorSnackRes> liquorSnackRes;
    // 판매처
    List<LiquorSellDto> liquorSellDtos;
    // 재료
    List<LiquorMaterialDto> liquorMaterialDtos;
    // 기분
    List<StateTypeDto> stateTypeDtos;
    // 맛
    List<TasteTypeDto> tasteTypeDtos;

    @ApiModelProperty(value = "검색한 유저의 해당 술 즐겨찾기 여부")
    Boolean isLike;
    @ApiModelProperty(value = "술 생성 일자")
    LocalDateTime createdAt;
    @ApiModelProperty(value = "술 수정 일자")
    LocalDateTime modifiedAt;

    public static LiquorTotalRes of (
            LiquorDto liquorDto,
            Boolean isLike,
            LiquorAbvDto liquorAbvDto,
            LiquorDetailDto liquorDetailDto,
            DrinkingCapacityDto drinkingCapacityDto,
            LiquorNameDto liquorNameDto,
            List<LiquorSnackDto> liquorSnackDtos,
            List<LiquorSellDto> liquorSellDtos,
            List<LiquorMaterialDto> liquorMaterialDtos,
            List<StateTypeDto> stateTypeDtos,
            List<TasteTypeDto> tasteTypeDtos
    ) {
        return new LiquorTotalRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                liquorDto.getLiquorRecipe(),
                liquorDto.getDetailAbv(),
                liquorDto.getLiquorFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + liquorDto.getLiquorFileNm(),
                liquorAbvDto,
                liquorDetailDto,
                drinkingCapacityDto,
                liquorNameDto,
                liquorSnackDtos.stream().map(LiquorSnackRes::from).collect(Collectors.toList()),
                liquorSellDtos,
                liquorMaterialDtos,
                stateTypeDtos,
                tasteTypeDtos,
                isLike,
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }

    public static LiquorTotalRes of (
            LiquorDto liquorDto,
            Optional<LiquorLike> isLike,
            Optional<LiquorAbvDto> liquorAbvDto,
            Optional<LiquorDetailDto> liquorDetailDto,
            Optional<DrinkingCapacityDto> drinkingCapacityDto,
            Optional<LiquorNameDto> liquorNameDto,
            List<LiquorSnackDto> liquorSnackDtos,
            List<LiquorSellDto> liquorSellDtos,
            List<LiquorMaterialDto> liquorMaterialDtos,
            List<StateTypeDto> stateTypeDtos,
            List<TasteTypeDto> tasteTypeDtos
    ) {
        return new LiquorTotalRes(
                liquorDto.getId(),
                liquorDto.getName(),
                liquorDto.getSummaryExplanation(),
                liquorDto.getDetailExplanation(),
                liquorDto.getLiquorRecipe(),
                liquorDto.getDetailAbv(),
                liquorDto.getLiquorFileNm() == null ? null :
                        FileUrl.FILE_DOWN_URL.getUrl() + liquorDto.getLiquorFileNm(),
                liquorAbvDto.orElse(null),
                liquorDetailDto.orElse(null),
                drinkingCapacityDto.orElse(null),
                liquorNameDto.orElse(null),
                liquorSnackDtos.stream().map(LiquorSnackRes::from).collect(Collectors.toList()),
                liquorSellDtos,
                liquorMaterialDtos,
                stateTypeDtos,
                tasteTypeDtos,
                isLike.isPresent(),
                liquorDto.getCreatedAt(),
                liquorDto.getModifiedAt()
        );
    }
}
