package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.service.party.PartyAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/party")
@Api(tags = "[ADMIN] 모임 및 모임 댓글 관리")
public class PartyAdminController {
    private final PartyAdminService partyAdminService;

    @DeleteMapping("/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임 삭제",
            notes = "해당 모임을 삭제처리 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "모임 기본키", dataTypeClass = Long.class)
    })
    public ApiDataResponse<Boolean> deleteParty(
            @PathVariable Long partyPriKey
    ) {
        return ApiDataResponse.of(
                partyAdminService.deleteParty(
                        partyPriKey
                )
        );
    }

    @DeleteMapping("/comment/{commentPriKey}")
    @ApiOperation(
            value = "모임 댓글 삭제",
            notes = "해당 모임을 삭제처리 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentPriKey", value = "모임 댓글 기본키", dataTypeClass = String.class)
    })
    public ApiDataResponse<Boolean> deleteParty(
            @PathVariable String commentPriKey
    ) {
        return ApiDataResponse.of(
                partyAdminService.deletePartyComment(
                        commentPriKey
                )
        );
    }
}
