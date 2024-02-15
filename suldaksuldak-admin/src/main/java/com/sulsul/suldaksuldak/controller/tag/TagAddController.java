package com.sulsul.suldaksuldak.controller.tag;

import com.sulsul.suldaksuldak.service.tag.TagAddService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.snack.LiquorSnackReq;
import com.sulsul.suldaksuldak.dto.tag.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/tag/add")
@Api(tags = "[ADMIN] 태그 추가")
public class TagAddController {
    private final TagAddService tagAddService;

    @ApiOperation(
            value = "주량 저장",
            notes = "주량 정도를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/drinking-capacity")
    public ApiDataResponse<Boolean> createDrinkingCapacity(
            @RequestBody DrinkingCapacityDto drinkingCapacityDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createDrinkingCapacity(
                        drinkingCapacityDto.getId(),
                        drinkingCapacityDto.getName(),
                        drinkingCapacityDto.getColor()
                )
        );
    }

    @ApiOperation(
            value = "도수 저장",
            notes = "도수 정도를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor-abv")
    public ApiDataResponse<Boolean> createLiquorAbv (
            @RequestBody LiquorAbvDto liquorAbvDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorAbv(
                        liquorAbvDto.getId(),
                        liquorAbvDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "2차 분류 저장",
            notes = "2차 분류를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor-detail")
    public ApiDataResponse<Boolean> createLiquorDetail (
            @RequestBody LiquorDetailDto liquorDetailDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorDetail(
                        liquorDetailDto.getId(),
                        liquorDetailDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "재료 저장",
            notes = "재료를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor-material")
    public ApiDataResponse<Boolean> createLiquorMaterial (
            @RequestBody LiquorMaterialDto liquorMaterialDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorMaterial(
                        liquorMaterialDto.getId(),
                        liquorMaterialDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "1차 분류 생성",
            notes = "1차 분류를 생성합니다."
    )
    @PostMapping(value = "/liquor-name", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<Boolean> createLiquorName (
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "liquorNameDto") LiquorNameDto liquorNameDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorName(
                        null,
                        liquorNameDto.getName(),
                        file
                )
        );
    }

    @ApiOperation(
            value = "1차 분류 수정",
            notes = "1차 분류를 수정합니다."
    )
    @PutMapping(value = "/liquor-name/{priKey:[0-9]+}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<Boolean> modifiedLiquorName (
            @PathVariable Long priKey,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "liquorNameDto") LiquorNameDto liquorNameDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorName(
                        priKey,
                        liquorNameDto.getName(),
                        file
                )
        );
    }

    @ApiOperation(
            value = "판매처 저장",
            notes = "판매처를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor-sell")
    public ApiDataResponse<Boolean> createLiquorSell (
            @RequestBody LiquorSellDto liquorSellDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorSell(
                        liquorSellDto.getId(),
                        liquorSellDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "상태(기분) 저장",
            notes = "상태(기분)를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/state-type")
    public ApiDataResponse<Boolean> createStateType (
            @RequestBody StateTypeDto stateTypeDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createStateType(
                        stateTypeDto.getId(),
                        stateTypeDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "맛 종류 저장",
            notes = "맛 종류를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/taste-type")
    public ApiDataResponse<Boolean> createStateType (
            @RequestBody TasteTypeDto tasteTypeDto
    ) {
        return ApiDataResponse.of(
                tagAddService.createTasteType(
                        tasteTypeDto.getId(),
                        tasteTypeDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "추천 안주 저장",
            notes = "추천 안주를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor-snack")
    public ApiDataResponse<Boolean> createLiquorSnackRecipe(
            @RequestBody LiquorSnackReq liquorSnackReq
    ) {
        return ApiDataResponse.of(
                tagAddService.createLiquorSnack(liquorSnackReq.toDto())
        );
    }
}
