package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.tag.snack.LiquorSnackRes;
import com.sulsul.suldaksuldak.dto.party.tag.PartyTagDto;
import com.sulsul.suldaksuldak.dto.tag.*;
import com.sulsul.suldaksuldak.service.common.TagViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tag/view")
@Api(tags = "[COMMON] 태그 조회")
public class TagViewController {
    private final TagViewService tagViewService;

    @ApiOperation(
            value = "모든 주량 조회",
            notes = "모든 주량을 조회 합니다."
    )
    @GetMapping(value = "/drinking-capacity")
    public ApiDataResponse<List<DrinkingCapacityDto>> getDrinkingCapacity() {
        return ApiDataResponse.of(tagViewService.getDrinkingCapacityDtoList());
    }

    @ApiOperation(
            value = "모든 도수 조회",
            notes = "모든 도수을 조회 합니다."
    )
    @GetMapping(value = "/liquor-abv")
    public ApiDataResponse<List<LiquorAbvDto>> getLiquorAbv() {
        return ApiDataResponse.of(tagViewService.getLiquorAbvDtoList());
    }

    @ApiOperation(
            value = "모든 2차 분류 조회",
            notes = "모든 2차 분류를 조회 합니다."
    )
    @GetMapping(value = "/liquor-detail")
    public ApiDataResponse<List<LiquorDetailDto>> getLiquorDetail() {
        return ApiDataResponse.of(tagViewService.getLiquorDetailDtoList());
    }

    @ApiOperation(
            value = "모든 재료 조회",
            notes = "모든 재료를 조회 합니다."
    )
    @GetMapping(value = "/liquor-material")
    public ApiDataResponse<List<LiquorMaterialDto>> getLiquorMaterial() {
        return ApiDataResponse.of(tagViewService.getLiquorMaterialDtoList());
    }

    @ApiOperation(
            value = "모든 1차 분류 조회",
            notes = "모든 1차 분류를 조회 합니다."
    )
    @GetMapping(value = "/liquor-name")
    public ApiDataResponse<List<LiquorNameDto>> getLiquorName() {
        return ApiDataResponse.of(tagViewService.getLiquorNameDtoList());
    }

    @ApiOperation(
            value = "모든 판매처 조회",
            notes = "모든 판매처를 조회 합니다."
    )
    @GetMapping(value = "/liquor-sell")
    public ApiDataResponse<List<LiquorSellDto>> getLiquorSell() {
        return ApiDataResponse.of(tagViewService.getLiquorSellDtoList());
    }

    @ApiOperation(
            value = "모든 상태 조회",
            notes = "모든 상태을 조회 합니다."
    )
    @GetMapping(value = "/state-type")
    public ApiDataResponse<List<StateTypeDto>> getStateType() {
        return ApiDataResponse.of(tagViewService.getStateTypeDtoList());
    }

    @ApiOperation(
            value = "모든 맛 조회",
            notes = "모든 맛을 조회 합니다."
    )
    @GetMapping(value = "/taste-type")
    public ApiDataResponse<List<TasteTypeDto>> getTasteType() {
        return ApiDataResponse.of(tagViewService.getTasteTypeDtoList());
    }

    @ApiOperation(
            value = "모든 안주 조회",
            notes = "모든 안주을 조회 합니다."
    )
    @GetMapping(value = "/liquor-snack")
    public ApiDataResponse<List<LiquorSnackRes>> getLiquorSnack() {
        return ApiDataResponse.of(
                tagViewService.getLiquorSnackDtoList()
                        .stream()
                        .map(LiquorSnackRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "모든 모임 태그 조회",
            notes = "모든 모임 태그를 조회 합니다."
    )
    @GetMapping(value = "/party-tag")
    public ApiDataResponse<List<PartyTagDto>> getPartyTag() {
        return ApiDataResponse.of(
                tagViewService.getPartyTagDtoList()
        );
    }
}
