package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.feedback.PartyFeedbackDto;
import com.sulsul.suldaksuldak.service.common.PartyFeedbackViewService;
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
@RequestMapping("/api/party-feedback/view")
@Api(tags = "[COMMON] 모임 피드백 정보 조회")
public class PartyFeedbackViewController {
    private final PartyFeedbackViewService partyFeedbackViewService;

    @ApiOperation(
            value = "모임 피드백 내용 조회",
            notes = "모임 피드백 목록을 조회합니다."
    )
    @GetMapping("/list")
    public ApiDataResponse<List<PartyFeedbackDto>> getPartyFeedbackDtoList() {
        return ApiDataResponse.of(
                partyFeedbackViewService.getPartyFeedbackList()
        );
    }
}
