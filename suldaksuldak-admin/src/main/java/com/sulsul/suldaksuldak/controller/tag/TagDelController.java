package com.sulsul.suldaksuldak.controller.tag;

import com.sulsul.suldaksuldak.Service.tag.TagDelService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/drinking-capacity")
    public ApiDataResponse<Boolean> deleteDrinkingCapacity(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteDrinkingCapacity(priKey)
        );
    }

    @ApiOperation(
            value = "도수 삭제",
            notes = "도수 정보를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-abv")
    public ApiDataResponse<Boolean> deleteLiquorAbv(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorAbv(priKey)
        );
    }

    @ApiOperation(
            value = "2차 분류 삭제",
            notes = "2차 분류를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-detail")
    public ApiDataResponse<Boolean> deleteLiquorDetail(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorDetail(priKey)
        );
    }

    @ApiOperation(
            value = "재료 삭제",
            notes = "재료 정보를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-material")
    public ApiDataResponse<Boolean> deleteLiquorMaterial(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorMaterial(priKey)
        );
    }

    @ApiOperation(
            value = "1차 분류 삭제",
            notes = "1차 분류를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-name")
    public ApiDataResponse<Boolean> deleteLiquorName(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorName(priKey)
        );
    }

    @ApiOperation(
            value = "판매처 삭제",
            notes = "판매처를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-sell")
    public ApiDataResponse<Boolean> deleteLiquorSell(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorSell(priKey)
        );
    }

    @ApiOperation(
            value = "상태 정보 삭제",
            notes = "상태 정보를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/state-type")
    public ApiDataResponse<Boolean> deleteStateType(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteStateType(priKey)
        );
    }
    @ApiOperation(
            value = "맛 정보 삭제",
            notes = "맛 정보를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/taste-type")
    public ApiDataResponse<Boolean> deleteTasteType(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteTasteType(priKey)
        );
    }

    @ApiOperation(
            value = "안주 삭제",
            notes = "안주를 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "태그의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/liquor-snack")
    public ApiDataResponse<Boolean> deleteLiquorSnack(
            Long priKey
    ) {
        return ApiDataResponse.of(
                tagDelService.deleteLiquorSnack(priKey)
        );
    }
}
