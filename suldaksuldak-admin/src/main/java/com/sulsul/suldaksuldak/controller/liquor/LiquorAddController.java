package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.LiquorReq;
import com.sulsul.suldaksuldak.service.liquor.LiquorAddService;
import com.sulsul.suldaksuldak.service.liquor.LiquorTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/add")
@Api(tags = "[ADMIN] 술 관련 정보 추가")
public class LiquorAddController {
    private final LiquorAddService liquorAddService;
    private final LiquorTagService liquorTagService;

    @ApiOperation(
            value = "술 생성",
            notes = "술 데이터를 생성합니다."
    )
    @PostMapping(
            value = "/liquor",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiDataResponse<Boolean> createLiquor(
            @RequestPart("liquorReq") LiquorReq liquorReq,
            @RequestPart(value = "file") MultipartFile file
    ) {
        Long saveLiquorPriKey = liquorAddService.createLiquor(
                liquorReq.toDto(null),
                file
        );
        return ApiDataResponse.of(
                liquorTagService.createLiquorTag(
                        liquorReq.toTotalReq(
                                saveLiquorPriKey
                        )
                )
        );
    }

    @ApiOperation(
            value = "술 수정",
            notes = "술 데이터를 수정합니다."
    )
    @PutMapping(
            value = "/liquor/{priKey:[0-9]+}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiDataResponse<Boolean> modifiedLiquor(
            @PathVariable Long priKey,
            @RequestPart("liquorReq") LiquorReq liquorReq,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Long saveLiquorPriKey = liquorAddService.createLiquor(
                liquorReq.toDto(priKey),
                file
        );
        return ApiDataResponse.of(
                liquorTagService.createLiquorTag(
                        liquorReq.toTotalReq(
                                saveLiquorPriKey
                        )
                )
        );
    }

    @ApiOperation(
            value = "술 레시피 관리",
            notes = "술의 레시피를 저장하거나 수정합니다,"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liquorId", value = "술의 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "liquorRecipe", value = "술 레시피", required = true, dataTypeClass = String.class)
    })
    @PutMapping(value = "/liquor-recipe")
    public ApiDataResponse<Boolean> putLiquorRecipe(
            Long liquorId,
            String liquorRecipe
    ) {
        return ApiDataResponse.of(liquorAddService.putLiquorRecipe(liquorId, liquorRecipe));
    }
}
