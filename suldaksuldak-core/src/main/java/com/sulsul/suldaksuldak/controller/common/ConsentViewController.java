package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemReq;
import com.sulsul.suldaksuldak.dto.admin.consent.ConsentItemRes;
import com.sulsul.suldaksuldak.service.common.ConsentViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/consent/view")
@Api(tags = "[COMMON] 동의 항목 관련 정보 조회")
public class ConsentViewController {
    private final ConsentViewService consentViewService;

    @ApiOperation(
            value = "동의 항목 정보 조회",
            notes = "동의 항목 타입과 순서에 맞게 정보 조회"
    )
    @GetMapping(value = "/consent")
    public ApiDataResponse<List<ConsentItemRes>> getConsentList(
            ConsentItemReq consentItemReq
    ) {
        return ApiDataResponse.of(
                consentViewService.getConsentList(consentItemReq.toDto())
                        .stream()
                        .map(ConsentItemRes::from)
                        .collect(Collectors.toList())
        );
    }

}