package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.service.liquor.LiquorDelService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.bridge.BridgeReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/del")
@Api(tags = "[ADMIN] 술 관련 정보 삭제")
public class LiquorDelController {
    private final LiquorDelService liquorDelService;

    @ApiOperation(
            value = "술 삭제",
            notes = "술을 삭제 처리 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "술 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deleteLiquor(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteLiquor(priKey)
        );
    }

    @ApiOperation(
            value = "재료와 술 관계 삭제",
            notes = "재료와 술의 관계를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-material")
    public ApiDataResponse<Boolean> deleteLiquorToLiquorMaterial(
            @RequestBody BridgeReq bridgeReq
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteMtToLi(bridgeReq.getLiquorPriKey(), bridgeReq.getTagPriKeys())
        );
    }

    @ApiOperation(
            value = "판매처와 술 관계 삭제",
            notes = "판매처와 술의 관계를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-sell")
    public ApiDataResponse<Boolean> deleteLiquorToLiquorSell(
            @RequestBody BridgeReq bridgeReq
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteSlToLi(bridgeReq.getLiquorPriKey(), bridgeReq.getTagPriKeys())
        );
    }

    @ApiOperation(
            value = "안주와 술 관계 삭제",
            notes = "안주와 술의 관계를 삭제합니다."
    )
    @DeleteMapping(value = "/liquor-snack")
    public ApiDataResponse<Boolean> deleteLiquorToLiquorSnack(
            @RequestBody BridgeReq bridgeReq
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteSnToLi(bridgeReq.getLiquorPriKey(), bridgeReq.getTagPriKeys())
        );
    }

    @ApiOperation(
            value = "상태와 술 관계 삭제",
            notes = "상태와 술의 관계를 삭제합니다."
    )
    @DeleteMapping(value = "/state-type")
    public ApiDataResponse<Boolean> deleteLiquorToStateType(
            @RequestBody BridgeReq bridgeReq
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteStToLi(bridgeReq.getLiquorPriKey(), bridgeReq.getTagPriKeys())
        );
    }

    @ApiOperation(
            value = "맛과 술 관계 삭제",
            notes = "맛과 술의 관계를 삭제합니다."
    )
    @DeleteMapping(value = "/taste-type")
    public ApiDataResponse<Boolean> deleteLiquorToTasteType(
            @RequestBody BridgeReq bridgeReq
    ) {
        return ApiDataResponse.of(
                liquorDelService.deleteTtToLi(bridgeReq.getLiquorPriKey(), bridgeReq.getTagPriKeys())
        );
    }
}
