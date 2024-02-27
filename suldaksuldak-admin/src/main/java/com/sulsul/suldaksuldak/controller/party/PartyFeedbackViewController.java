package com.sulsul.suldaksuldak.controller.party;


import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackRes;
import com.sulsul.suldaksuldak.service.party.PartyFeedbackViewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/party-feedback/view")
@Api(tags = "[ADMIN] 모임 피드백 내용 조회")
public class PartyFeedbackViewController {
    private final PartyFeedbackViewService partyFeedbackViewService;

    @GetMapping("/list")
    public ApiDataResponse<List<UserPartyFeedbackRes>> getUserPartyFeedbackList(
            FeedbackType feedbackType,
            String comment,
            Long partyPriKey,
            Long writerPriKey,
            Long targetUserPriKey,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        return ApiDataResponse.of(
                partyFeedbackViewService.getUserPartyFeedbackDtoList(
                        feedbackType,
                        comment,
                        partyPriKey,
                        writerPriKey,
                        targetUserPriKey,
                        startAt,
                        endAt
                ).stream().map(UserPartyFeedbackRes::from).toList()
        );
    }

}
