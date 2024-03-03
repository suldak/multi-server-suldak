package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.service.party.PartyTagAdminService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.tag.PartyTagDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/party-tag")
@Api(tags = "[ADMIN] 모임 태그 관리")
public class PartyTagAdminController {
    private final PartyTagAdminService partyTagAdminService;

    @ApiOperation(
            value = "모임 태그 생성 및 수정",
            notes = "모임 태그를 생성하거나 수정합니다."
    )
    @PostMapping
    public ApiDataResponse<Boolean> createPartyTag(
            @RequestBody PartyTagDto partyTagDto
    ) {
        return ApiDataResponse.of(
                partyTagAdminService.createPartyTag(
                        partyTagDto.getId(),
                        partyTagDto.getName()
                )
        );
    }

    @ApiOperation(
            value = "모임 태그 삭제",
            notes = "모임 태그를 삭제합니다."
    )
    @DeleteMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> deletePartyTag(
            @PathVariable Long priKey
    ) {
        return ApiDataResponse.of(
                partyTagAdminService.deletePartyTag(priKey)
        );
    }
}
