package com.sulsul.suldaksuldak.controller.admin;

import com.sulsul.suldaksuldak.Service.admin.ConsentService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/consent")
@Api(tags = "[ADMIN] 동의 항목 관련 정보 관리")
public class ConsentController {
    private final ConsentService consentService;

    @ApiOperation(
            value = "동의 항목 저장",
            notes = "동의 항목을 생성하거나 수정합니다. (HTML 문법으로 저장해야 할 듯 합니다. (줄넘김, 두꺼운 글씨 등)"
    )
    @PostMapping(value = "/consent")
    public ApiDataResponse<Boolean> createConsent(
            @RequestBody ConsentItemReq consentItemReq
    ) {
        return ApiDataResponse.of(consentService.createConsentItem(consentItemReq.toDto()));
    }

    @ApiOperation(
            value = "동의 항목 삭제",
            notes = "동의 항목을 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "동의 항목 기본키", required = true, dataTypeClass = Long.class),
    })
    @DeleteMapping(value = "/consent/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deleteConsent(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(consentService.deleteConsentItem(priKey));
    }
}
