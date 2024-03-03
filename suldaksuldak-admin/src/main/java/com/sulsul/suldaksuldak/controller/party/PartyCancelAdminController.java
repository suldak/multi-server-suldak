package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.cancel.PartyCancelReasonDto;
import com.sulsul.suldaksuldak.service.party.PartyCancelAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/party-cancel")
@Api(tags = "[ADMIN] 모임 취소 이유 관리")
public class PartyCancelAdminController {
    private final PartyCancelAdminService partyCancelAdminService;

    @ApiOperation(
            value = "모임 취소 이유 생성 및 수정",
            notes = "모임 취소 이유를 생성하거나 수정합니다."
    )
    @PostMapping
    public ApiDataResponse<Boolean> createPartyCancelReason(
            @RequestBody PartyCancelReasonDto partyCancelReasonDto
    ) {
        return ApiDataResponse.of(
                partyCancelAdminService.createPartyCancelReason(
                        partyCancelReasonDto.getId(),
                        partyCancelReasonDto.getReason(),
                        partyCancelReasonDto.getPartyRoleType()
                )
        );
    }

    @ApiOperation(
            value = "모임 취소 이유 삭제",
            notes = "모임 취소 이유를 삭제합니다."
    )
    @DeleteMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deletePartyCancelReason(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                partyCancelAdminService.deletePartyCancelReason(
                        priKey
                )
        );
    }
}
