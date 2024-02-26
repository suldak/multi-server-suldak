package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.component.batch.ToBatchServer;
import com.sulsul.suldaksuldak.constant.batch.BatchServerUrl;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyHostService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/party/host")
@Api(tags = "[MAIN] 모임 관리 (호스트 전용)")
public class PartyHostController {
    private final ToBatchServer toBatchServer;
    private final PartyHostService partyHostService;

    @PutMapping("/confirm/{priKey}")
    @ApiOperation(
            value = "유저에 대한 모임 참가 결정 (모임 신청 정보의 기본키 이용)",
            notes = "해당 모임 참가를 결정합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 신청 정보의 기본키 (String으로 된 값)", dataTypeClass = String.class),
            @ApiImplicitParam(name = "confirm", value = "결정 여부 (CONFIRM (승인), REFUSE (거절))")
    })
    public ApiDataResponse<Boolean> confirmPartyGuest(
            HttpServletRequest request,
            @PathVariable String priKey,
            GuestType confirm
    ) {
        if (!confirm.equals(GuestType.CONFIRM) && !confirm.equals(GuestType.REFUSE)) {
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "CONFIRM(승인) 이나 REFUSE(거절) 중 하나를 입력해주세요."
            );
        }
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyHostService.modifiedPartyGuest(
                        requestUserPriKey,
                        priKey,
                        confirm
                )
        );
    }

    @PutMapping("/recruitment-end/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 모집 종료 설정",
            notes = "해당 모임의 모집을 종료합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> setPartyRecruitmentEnd(
            @PathVariable Long partyPriKey,
            HttpServletRequest request
    ) {
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        partyHostService.modifiedPartyState(
                partyPriKey,
                requestUserPriKey,
                PartyStateType.RECRUITMENT_END
        );
        return ApiDataResponse.of(
                toBatchServer.partyBatchApiToBatchServer(
                        partyPriKey,
                        BatchServerUrl.PARTY_SCHEDULE_RECRUITMENT_END_URL,
                        HttpMethod.PUT
                )
        );
    }

    @PutMapping("/meeting-cancle/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 취소 설정",
            notes = "해당 모임을 취소합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> setPartyMeetingCancel(
            @PathVariable Long partyPriKey,
            HttpServletRequest request
    ) {
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        partyHostService.modifiedPartyState(
                partyPriKey,
                requestUserPriKey,
                PartyStateType.MEETING_CANCEL
        );
        return ApiDataResponse.of(
                toBatchServer.partyBatchApiToBatchServer(
                        partyPriKey,
                        HttpMethod.DELETE
                )
        );
    }

    @PutMapping("/meeting-delete/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 삭제 처리",
            notes = "해당 모임을 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> setPartyMeetingDelete(
            @PathVariable Long partyPriKey,
            HttpServletRequest request
    ) {
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        partyHostService.modifiedPartyState(
                partyPriKey,
                requestUserPriKey,
                PartyStateType.MEETING_DELETE
        );
        return ApiDataResponse.of(
                toBatchServer.partyBatchApiToBatchServer(
                        partyPriKey,
                        HttpMethod.DELETE
                )
        );
    }
}
