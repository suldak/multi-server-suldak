package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.feedback.PartyFeedbackDto;
import com.sulsul.suldaksuldak.service.party.PartyFeedbackAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/party-feedback")
@Api(tags = "[ADMIN] 모임 피드백 관리")
public class PartyFeedbackAdminController {
    private final PartyFeedbackAdminService partyFeedbackAdminService;

    @ApiOperation(
            value = "모임 피드백 생성 및 수정",
            notes = "모임 피드백을 생성하거나 수정합니다."
    )
    @PostMapping
    public ApiDataResponse<Boolean> createPartyFeedback(
            @RequestBody PartyFeedbackDto partyFeedbackDto
    ) {
        return ApiDataResponse.of(
                partyFeedbackAdminService
                        .createPartyFeedback(
                                partyFeedbackDto.getId(),
                                partyFeedbackDto.getFeedBackText(),
                                partyFeedbackDto.getScore()
                        )
        );
    }

    @ApiOperation(
            value = "모임 태그 생성 및 수정",
            notes = "모임 태그를 생성하거나 수정합니다."
    )
    @DeleteMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deletePartyFeedback(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                partyFeedbackAdminService.deletePartyFeedback(priKey)
        );
    }
}
