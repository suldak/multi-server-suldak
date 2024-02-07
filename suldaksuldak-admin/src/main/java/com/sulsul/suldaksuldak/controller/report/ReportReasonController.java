package com.sulsul.suldaksuldak.controller.report;

import com.sulsul.suldaksuldak.service.report.ReportReasonService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyReasonDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/report/reason")
@Api(tags = "[ADMIN] 신고 이유 관리")
public class ReportReasonController {
    private final ReportReasonService reportReasonService;

    @PostMapping("/party")
    @ApiOperation(
            value = "모임 신고 생성 및 수정",
            notes = "모임 신고 이유를 생성하거나 수정합니다."
    )
    public ApiDataResponse<Boolean> createReportPartyReason(
            @RequestBody ReportPartyReasonDto reportPartyReasonDto
    ) {
        return ApiDataResponse.of(
                reportReasonService.createReportPartyReason(
                        reportPartyReasonDto.getId(),
                        reportPartyReasonDto.getReason()
                )
        );
    }

    @DeleteMapping("/party/{priKey:[0-9]+}")
    @ApiOperation(
            value = "모임 신고 삭제",
            notes = "모임 신고 이유를 삭제합니다."
    )
    public ApiDataResponse<Boolean> deleteReportPartyReason(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                reportReasonService.deleteReportPartyReason(priKey)
        );
    }
}
