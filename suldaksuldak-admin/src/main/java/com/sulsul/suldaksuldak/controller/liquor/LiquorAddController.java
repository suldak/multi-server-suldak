package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.Service.liquor.LiquorAddService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.liquor.liquor.LiquorReq;
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
@RequestMapping("/api/admin/liquor/add")
@Api(tags = "[ADMIN] 술 관련 정보 추가")
public class LiquorAddController {
    private final LiquorAddService liquorAddService;

    @ApiOperation(
            value = "술 저장",
            notes = "술 데이터를 생성하거나 수정합니다."
    )
    @PostMapping(value = "/liquor")
    public ApiDataResponse<Boolean> createLiquor(
            @RequestBody LiquorReq liquorReq
    ) {
        return ApiDataResponse.of(
                liquorAddService.createLiquor(liquorReq.toDto())
        );
    }
}
