package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyCommentRes;
import com.sulsul.suldaksuldak.dto.report.party.ReportPartyRes;
import com.sulsul.suldaksuldak.service.common.ReportViewService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/report/view")
@Api(tags = "[COMMON] 신고 내용 조회")
public class ReportViewController {
    private final ReportViewService reportViewService;

    @GetMapping("/party")
    @ApiOperation(
            value = "모임 신고 내역 조회",
            notes = "모임을 신고한 내역들을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "신고한 유저 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "partyPriKey", value = "신고 당한 모임 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "reportReasonPriKey", value = "모임 신고 이유 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "complete", value = "처리 여부", dataTypeClass = Boolean.class),
    })
    public ApiDataResponse<List<ReportPartyRes>> getReportPartyList(
            Long userPriKey,
            Long partyPriKey,
            Long reportReasonPriKey,
            Boolean complete
    ) {
        return ApiDataResponse.of(
                reportViewService.getReportPartyList(
                                userPriKey,
                                partyPriKey,
                                reportReasonPriKey,
                                complete
                        )
                        .stream()
                        .map(ReportPartyRes::from)
                        .toList()
        );
    }

    @GetMapping("/party-comment/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 댓글 신고 내역 조회",
            notes = "모임 댓글을 신고한 내역들을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "신고한 유저 기본키 (NULL이면 자신의 신고 내역 조회)", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
    public ApiDataResponse<List<ReportPartyCommentRes>> getReportPartyCommentList(
            HttpServletRequest request,
            Long userPriKey,
            @PathVariable Long partyPriKey
    ) {
        Long searchUserPriKey = userPriKey != null ? userPriKey : UtilTool.getUserPriKeyFromHeader(request);

        return ApiDataResponse.of(
                reportViewService.getReportPartyCommentList(
                        searchUserPriKey,
                        partyPriKey
                ).stream().map(ReportPartyCommentRes::from).toList()
        );
    }
}
