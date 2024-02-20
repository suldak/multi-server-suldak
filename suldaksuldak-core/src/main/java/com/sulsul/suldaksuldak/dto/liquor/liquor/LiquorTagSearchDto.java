package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.tag.LiquorMaterialDto;
import com.sulsul.suldaksuldak.dto.tag.LiquorSellDto;
import com.sulsul.suldaksuldak.dto.tag.StateTypeDto;
import com.sulsul.suldaksuldak.dto.tag.TasteTypeDto;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "태그 검색 Reqeust")
public class LiquorTagSearchDto {
    @ApiModelProperty(value = "추천 안주 기본키 리스트")
    List<Long> snackPriKeys;
    @ApiModelProperty(value = "판매처 기본키 리스트")
    List<Long> sellPriKeys;
    @ApiModelProperty(value = "재료 기본키 리스트")
    List<Long> materialPriKeys;
    @ApiModelProperty(value = "기분 기본키 리스트")
    List<Long> statePriKeys;
    @ApiModelProperty(value = "맛 기본키 리스트")
    List<Long> tastePriKeys;
    @ApiModelProperty(value = "도수 기본키")
    List<Long> liquorAbvPriKeys;
    @ApiModelProperty(value = "2차 분류 기본키")
    List<Long> liquorDetailPriKeys;
    @ApiModelProperty(value = "주량 기본키")
    List<Long> drinkingCapacityPriKeys;
    @ApiModelProperty(value = "1차 분류 기본키")
    List<Long> liquorNamePriKeys;
    @ApiModelProperty(value = "술 검색을 위한 문구")
    String searchTag;
    @ApiModelProperty(value = "true: 태그에 맞는 정확한 검색 (and 조건), false: 태그가 포함된 모든 술 조회 (or 조건)")
    Boolean andBool;
    @ApiModelProperty(value = "페이지 번호 (0이 시작)")
    Integer pageNum;
    @ApiModelProperty(value = "페이지 사이즈")
    Integer recordSize;

    public static LiquorTagSearchDto of (
            List<Long> snackPriKeys,
            List<Long> sellPriKeys,
            List<Long> materialPriKeys,
            List<Long> statePriKeys,
            List<Long> tastePriKeys,
            List<Long> liquorAbvPriKeys,
            List<Long> liquorDetailPriKeys,
            List<Long> drinkingCapacityPriKeys,
            List<Long> liquorNamePriKeys,
            String searchTag,
            Boolean andBool,
            Integer pageNum,
            Integer recordSize
    ) {
        return new LiquorTagSearchDto(
                snackPriKeys,
                sellPriKeys,
                materialPriKeys,
                statePriKeys,
                tastePriKeys,
                liquorAbvPriKeys,
                liquorDetailPriKeys,
                drinkingCapacityPriKeys,
                liquorNamePriKeys,
                searchTag,
                andBool,
                pageNum,
                recordSize
        );
    }

    public static LiquorTagSearchDto emptyListOf(
            Boolean andBool
    ) {
        return new LiquorTagSearchDto(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                andBool,
                0,
                10
        );
    }

    public void addTags(
            LiquorTagSearchDto liquorTagSearchDto
    ) {
        snackPriKeys.addAll(liquorTagSearchDto.getSnackPriKeys());
        sellPriKeys.addAll(liquorTagSearchDto.getSellPriKeys());
        materialPriKeys.addAll(liquorTagSearchDto.getMaterialPriKeys());
        statePriKeys.addAll(liquorTagSearchDto.getStatePriKeys());
        tastePriKeys.addAll(liquorTagSearchDto.getTastePriKeys());
        liquorAbvPriKeys.addAll(liquorTagSearchDto.getLiquorAbvPriKeys());
        liquorDetailPriKeys.addAll(liquorTagSearchDto.getLiquorDetailPriKeys());
        drinkingCapacityPriKeys.addAll(liquorTagSearchDto.getDrinkingCapacityPriKeys());
        liquorNamePriKeys.addAll(liquorTagSearchDto.getLiquorNamePriKeys());
    }

    public void addTags(
            LiquorTotalRes liquorTotalRes
    ) {
        snackPriKeys.addAll(liquorTotalRes.getLiquorSnackRes().stream().collect(Collectors.groupingBy(LiquorSnackRes::getId)).keySet());
        sellPriKeys.addAll(liquorTotalRes.getLiquorSellDtos().stream().collect(Collectors.groupingBy(LiquorSellDto::getId)).keySet());
        materialPriKeys.addAll(liquorTotalRes.getLiquorMaterialDtos().stream().collect(Collectors.groupingBy(LiquorMaterialDto::getId)).keySet());
        statePriKeys.addAll(liquorTotalRes.getStateTypeDtos().stream().collect(Collectors.groupingBy(StateTypeDto::getId)).keySet());
        tastePriKeys.addAll(liquorTotalRes.getTasteTypeDtos().stream().collect(Collectors.groupingBy(TasteTypeDto::getId)).keySet());
        liquorAbvPriKeys.add(liquorTotalRes.getLiquorAbvDto().getId());
        liquorDetailPriKeys.add(liquorTotalRes.getLiquorDetailDto().getId());
        drinkingCapacityPriKeys.add(liquorTotalRes.getDrinkingCapacityDto().getId());
        liquorNamePriKeys.add(liquorTotalRes.getLiquorNameDto().getId());
    }

    public void sortTags() {
        snackPriKeys = UtilTool.removeDuplicates(snackPriKeys);
        sellPriKeys = UtilTool.removeDuplicates(sellPriKeys);
        materialPriKeys = UtilTool.removeDuplicates(materialPriKeys);
        statePriKeys = UtilTool.removeDuplicates(statePriKeys);
        tastePriKeys = UtilTool.removeDuplicates(tastePriKeys);
        liquorAbvPriKeys = UtilTool.removeDuplicates(liquorAbvPriKeys);
        liquorDetailPriKeys = UtilTool.removeDuplicates(liquorDetailPriKeys);
        drinkingCapacityPriKeys = UtilTool.removeDuplicates(drinkingCapacityPriKeys);
        liquorNamePriKeys = UtilTool.removeDuplicates(liquorNamePriKeys);
    }
}
