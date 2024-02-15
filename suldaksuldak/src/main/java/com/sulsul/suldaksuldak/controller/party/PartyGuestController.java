package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyGuestService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/party/guest")
@Api(tags = "[MAIN] 모임 관리 (Guest 전용)")
public class PartyGuestController {
    private final PartyGuestService partyGuestService;

    @PutMapping("/enter/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 참가 신청",
            notes = "해당 모임의 자신이 참가를 신청합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> enterParty(
            HttpServletRequest request,
            @PathVariable Long partyPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);

        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );

        return ApiDataResponse.of(
                partyGuestService.participationParty(
                        partyPriKey,
                        userPriKey
                )
        );
    }

    @PutMapping("/cancel/{priKey}")
    @ApiOperation(
            value = "모임 참가 취소",
            notes = "해당 모임 참가를 취소합니다. (본인만 취소 가능)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 신청 정보의 기본키 (String으로 된 값)", dataTypeClass = String.class)
    })
    public ApiDataResponse<Boolean> cancelPartyGuest(
            HttpServletRequest request,
            @PathVariable String priKey
    ) {
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyGuestService.partyCancel(
                        requestUserPriKey,
                        priKey
                )
        );
    }
}
