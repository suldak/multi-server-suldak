package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.component.batch.ToBatchServer;
import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyStateType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyReq;
import com.sulsul.suldaksuldak.dto.admin.feedback.UserPartyFeedbackReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
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
    private final ToBatchServer toBatchServer;
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
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        Long partyPriKey = partyService.createParty(
                partyReq.toDto(
                        userPriKey,
                        PartyStateType.RECRUITING
                ),
                file
        );
        return ApiDataResponse.of(
                toBatchServer.partyBatchApiToBatchServer(
                        partyPriKey,
                        HttpMethod.POST
                )
        );
    }

    @ApiOperation(
            value = "모임 수정",
            notes = "기존 모임을 수정합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
    @PutMapping("/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> modifiedParty(
            HttpServletRequest request,
            @PathVariable Long priKey,
            @RequestBody PartyReq partyReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        partyService.modifiedParty(
                priKey,
                partyReq.toDto(
                        userPriKey,
                        null
                )
        );
        return ApiDataResponse.of(
                toBatchServer.partyBatchApiToBatchServer(
                        priKey,
                        HttpMethod.PUT
                )
        );
    }

    @ApiOperation(
            value = "모임의 사진 변경",
            notes = "모임의 사진을 변경합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
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

    @ApiOperation(
            value = "모임의 피드백 반영",
            notes = "모임의 피드백을 반영합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 기본키", required = true, dataTypeClass = Long.class)
    })
    @PutMapping("/feedback/{priKey:[0-9]+}")
    public ApiDataResponse<Boolean> createPartyFeedback(
            HttpServletRequest request,
            @RequestBody UserPartyFeedbackReq userPartyFeedbackReq,
            @PathVariable Long priKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (userPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyService.createUserPartyFeedback(
                        userPriKey,
                        priKey,
                        userPartyFeedbackReq.getFeedbackObjs()
                )
        );
    }
}
