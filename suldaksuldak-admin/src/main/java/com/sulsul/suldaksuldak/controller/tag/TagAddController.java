package com.sulsul.suldaksuldak.controller.tag;

import com.sulsul.suldaksuldak.Service.tag.TagAddService;
import com.sulsul.suldaksuldak.domain.tag.LiquorDetail;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.tag.DrinkingCapacityDto;
import com.sulsul.suldaksuldak.dto.tag.LiquorAbvDto;
import com.sulsul.suldaksuldak.dto.tag.LiquorDetailDto;
import com.sulsul.suldaksuldak.dto.tag.LiquorMaterialDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                        drinkingCapacityDto.getLevel()
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
}
