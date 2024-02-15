package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyReq;
import com.sulsul.suldaksuldak.service.party.PartyService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/party")
@Api(tags = "[MAIN] 모임 관리")
public class PartyController {
    private final PartyService partyService;

    @ApiOperation(
            value = "모임 생성",
            notes = "새로운 모임을 생성합니다."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<Boolean> createParty(
            HttpServletRequest request,
            @RequestPart("partyReq") PartyReq partyReq,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        return ApiDataResponse.of(
                partyService.createParty(
                        partyReq.toDto(
                                userPriKey,
                                PartyStateType.RECRUITING
                        ),
                        file
                )
        );
    }

    @ApiOperation(
            value = "모임 수정",
            notes = "기존 모임을 수정합니다."
    )
    @PutMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> modifiedParty(
            HttpServletRequest request,
            @PathVariable Long priKey,
            @RequestBody PartyReq partyReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        return ApiDataResponse.of(
                partyService.modifiedParty(
                        priKey,
                        partyReq.toDto(
                                userPriKey,
                                null
                        )
                )
        );
    }

    @ApiOperation(
            value = "모임의 사진 변경",
            notes = "모임의 사진을 변경합니다."
    )
    @PutMapping("/picture/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> modifiedPartyPicture(
            @PathVariable Long priKey,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiDataResponse.of(
                partyService.modifiedPartyPicture(
                        priKey,
                        file
                )
        );
    }
}
