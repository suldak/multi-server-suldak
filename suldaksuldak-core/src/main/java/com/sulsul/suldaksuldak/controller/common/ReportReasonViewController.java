package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyReasonDto;
import com.sulsul.suldaksuldak.service.common.ReportReasonViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/report/reason/view")
@Api(tags = "[COMMON] 신고 이유 조회")
public class ReportReasonViewController {
    private final ReportReasonViewService reportReasonViewService;

    @GetMapping("/party")
    @ApiOperation(
            value = "모임 신고 이유 조회",
            notes = "모임 신고 이유 목록을 조회합니다."
    )
    public ApiDataResponse<List<ReportPartyReasonDto>> getReportPartyReasonList() {
        return ApiDataResponse.of(
                reportReasonViewService
                        .getReportPartyReasonList()
        );
    }
}
