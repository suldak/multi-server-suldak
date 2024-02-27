package com.sulsul.suldaksuldak.controller.party;


import com.sulsul.suldaksuldak.constant.party.FeedbackType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackRes;
import com.sulsul.suldaksuldak.service.party.PartyFeedbackViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = "모임 피드백 목록을 조회합니다.",
            notes = "유저가 즐겨찾기를 많이 등록한 술 순서대로 목록을 조회합니다. (Pageable)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comment", value = "모임 피드백 상세 내용", dataTypeClass = String.class),
            @ApiImplicitParam(name = "searchStartTime", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "searchEndTime", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00"),
            @ApiImplicitParam(name = "feedbackType", value = "모임 피드백 타입"),
            @ApiImplicitParam(name = "partyPriKey", value = "검색 하고 싶은 모임의 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "writerPriKey", value = "피드백을 남김 유저의 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "targetUserPriKey", value = "피드백을 받은 유저의 기본키", dataTypeClass = Long.class),
    })
    @GetMapping("/list")
    public ApiDataResponse<List<UserPartyFeedbackRes>> getUserPartyFeedbackList(
            FeedbackType feedbackType,
            String comment,
            Long partyPriKey,
            Long writerPriKey,
            Long targetUserPriKey,
            LocalDateTime searchStartTime,
            LocalDateTime searchEndTime
    ) {
        return ApiDataResponse.of(
                partyFeedbackViewService.getUserPartyFeedbackDtoList(
                        feedbackType,
                        comment,
                        partyPriKey,
                        writerPriKey,
                        targetUserPriKey,
                        searchStartTime,
                        searchEndTime
                ).stream().map(UserPartyFeedbackRes::from).toList()
        );
    }

}
