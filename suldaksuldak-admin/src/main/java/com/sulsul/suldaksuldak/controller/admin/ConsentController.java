package com.sulsul.suldaksuldak.controller.admin;

import com.sulsul.suldaksuldak.Service.admin.ConsentService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/consent/admin")
@Api(tags = "[ADMIN] 동의 항목 관련 정보 관리")
public class ConsentController {
    private final ConsentService consentService;

    @ApiOperation(
            value = "동의 항목 저장",
            notes = "동의 항목을 생성하거나 수정합니다."
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
    @DeleteMapping(value = "/consent")
    public ApiDataResponse<Boolean> deleteConsent(
            @RequestBody ConsentItemReq consentItemReq
    ) {
        return ApiDataResponse.of(consentService.deleteConsentItem(consentItemReq.getId()));
    }
}
