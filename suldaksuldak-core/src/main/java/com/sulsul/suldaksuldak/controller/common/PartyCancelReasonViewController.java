package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.constant.party.PartyRoleType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelReasonDto;
import com.sulsul.suldaksuldak.service.common.PartyCancelReasonViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/api/party-cancel/view")
@Api(tags = "[COMMON] 모임 취소 이유 조회")
public class PartyCancelReasonViewController {
    private final PartyCancelReasonViewService partyCancelReasonViewService;

    @ApiOperation(
            value = "모임 취소 이유 조회",
            notes = "모임 취소 이유를 조회합니다"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyRoleType", value = "모임 역할에 따라서 조회 (HOST / GUEST)", dataTypeClass = String.class)
    })
    @GetMapping("/list")
    public ApiDataResponse<List<PartyCancelReasonDto>> getPartyCancelReasonList(
            PartyRoleType partyRoleType
    ) {
        return ApiDataResponse.of(
                partyCancelReasonViewService.getPartyCancelReasonList(
                        partyRoleType
                )
        );
    }
}
