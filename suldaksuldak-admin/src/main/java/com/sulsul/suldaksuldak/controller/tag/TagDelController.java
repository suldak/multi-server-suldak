package com.sulsul.suldaksuldak.controller.tag;

import com.sulsul.suldaksuldak.Service.tag.TagDelService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.tag.TagPriKeyReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/tag/del")
@Api(tags = "[ADMIN] 태그 삭제")
public class TagDelController {
    private final TagDelService tagDelService;

    @ApiOperation(
            value = "주량 삭제",
            notes = "주량 정보를 삭제합니다."
    )
    @DeleteMapping(value = "/drinking-capacity")
    public ApiDataResponse<Boolean> deleteDrinkingCapacity(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteDrinkingCapacity(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "도수 삭제",
            notes = "도수 정보를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-abv")
    public ApiDataResponse<Boolean> deleteLiquorAbv(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorAbv(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "2차 분류 삭제",
            notes = "2차 분류를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-detail")
    public ApiDataResponse<Boolean> deleteLiquorDetail(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorDetail(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "재료 삭제",
            notes = "재료 정보를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-material")
    public ApiDataResponse<Boolean> deleteLiquorMaterial(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorMaterial(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "1차 분류 삭제",
            notes = "1차 분류를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-name")
    public ApiDataResponse<Boolean> deleteLiquorName(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorName(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "판매처 삭제",
            notes = "판매처를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-sell")
    public ApiDataResponse<Boolean> deleteLiquorSell(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorSell(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "상태 정보 삭제",
            notes = "상태 정보를 삭제합니다."
    )
    @DeleteMapping(value = "/state-type")
    public ApiDataResponse<Boolean> deleteStateType(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteStateType(tagPriKeyReq.getPriKey())
        );
    }
    @ApiOperation(
            value = "맛 정보 삭제",
            notes = "맛 정보를 삭제합니다."
    )
    @DeleteMapping(value = "/taste-type")
    public ApiDataResponse<Boolean> deleteTasteType(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteTasteType(tagPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "안주 삭제",
            notes = "안주를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-snack")
    public ApiDataResponse<Boolean> deleteLiquorSnack(
            @RequestBody TagPriKeyReq tagPriKeyReq
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorSnack(tagPriKeyReq.getPriKey())
        );
    }
}
