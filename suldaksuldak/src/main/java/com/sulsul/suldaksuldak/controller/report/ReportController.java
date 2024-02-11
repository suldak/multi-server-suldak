package com.sulsul.suldaksuldak.controller.report;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.report.ReportService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/report/submit")
@Api(tags = "[MAIN] 신고 제출")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/party/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 신고",
            notes = "모임을 신고합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> submitPartyReport(
            HttpServletRequest request,
            @PathVariable Long partyPriKey,
            @RequestBody ReportPartyReq reportPartyReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                reportService.createReportParty(
                        userPriKey,
                        partyPriKey,
                        reportPartyReq.getReasonPriKey()
                )
        );
    }

    @PostMapping("/party-comment/{partyCommentPriKey}")
    @ApiOperation(
            value = "모임 댓글 신고",
            notes = "모임 댓글을 신고합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyCommentPriKey", value = "모임 댓글 기본키", required = true, dataTypeClass = String.class)
    })
    public ApiDataResponse<Boolean> submitPartyCommentReport(
            HttpServletRequest request,
            @PathVariable String partyCommentPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                reportService.createReportPartyComment(
                        userPriKey,
                        partyCommentPriKey
                )
        );
    }
}
