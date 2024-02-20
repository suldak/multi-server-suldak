package com.sulsul.suldaksuldak.dto.liquor.liquor;

import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "태그 검색 Reqeust")
public class LiquorTagSearchReq {
    @ApiModelProperty(value = "추천 안주 기본키 (\",\"로 구분한 기본키 String)")
    String snackPriKeys;
    @ApiModelProperty(value = "판매처 기본키 (\",\"로 구분한 기본키 String)")
    String sellPriKeys;
    @ApiModelProperty(value = "재료 기본키 (\",\"로 구분한 기본키 String)")
    String materialPriKeys;
    @ApiModelProperty(value = "기분 기본키 (\",\"로 구분한 기본키 String)")
    String statePriKeys;
    @ApiModelProperty(value = "맛 기본키 (\",\"로 구분한 기본키 String)")
    String tastePriKeys;
    @ApiModelProperty(value = "도수 기본키 (\",\"로 구분한 기본키 String)")
    String liquorAbvPriKeys;
    @ApiModelProperty(value = "2차 분류 기본키 (\",\"로 구분한 기본키 String)")
    String liquorDetailPriKeys;
    @ApiModelProperty(value = "주량 기본키 (\",\"로 구분한 기본키 String)")
    String drinkingCapacityPriKeys;
    @ApiModelProperty(value = "1차 분류 기본키 (\",\"로 구분한 기본키 String)")
    String liquorNamePriKeys;
    @ApiModelProperty(value = "술 검색을 위한 문구")
    String searchTag;
    @ApiModelProperty(value = "true: 태그에 맞는 정확한 검색 (and 조건), false: 태그가 포함된 모든 술 조회 (or 조건)")
    Boolean andBool;
    @ApiModelProperty(value = "페이지 번호 (0이 시작)")
    Integer pageNum;
    @ApiModelProperty(value = "페이지 사이즈")
    Integer recordSize;

    public LiquorTagSearchDto toDto () {
        return LiquorTagSearchDto.of(
                UtilTool.getSplitList(snackPriKeys, Long.class),
                UtilTool.getSplitList(sellPriKeys, Long.class),
                UtilTool.getSplitList(materialPriKeys, Long.class),
                UtilTool.getSplitList(statePriKeys, Long.class),
                UtilTool.getSplitList(tastePriKeys, Long.class),
                UtilTool.getSplitList(liquorAbvPriKeys, Long.class),
                UtilTool.getSplitList(liquorDetailPriKeys, Long.class),
                UtilTool.getSplitList(drinkingCapacityPriKeys, Long.class),
                UtilTool.getSplitList(liquorNamePriKeys, Long.class),
                searchTag,
                andBool,
                pageNum,
                recordSize
        );
    }
}
