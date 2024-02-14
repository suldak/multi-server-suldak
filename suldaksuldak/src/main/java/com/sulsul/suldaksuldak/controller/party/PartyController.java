package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyReq;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
                        partyReq.toDto(userPriKey),
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
                        partyReq.toDto(userPriKey)
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

    @PostMapping("/enter/{partyPriKey:[0-9]+}")
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
                partyService.participationParty(
                        partyPriKey,
                        userPriKey
                )
        );
    }

//    @PostMapping("/confirm/{partyPriKey:[0-9]+}")
//    @ApiOperation(
//            value = "모임 참가 결정 (모임과 유저의 기본키 이용)",
//            notes = "해당 모임 참가를 결정합니다."
//    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "partyPriKey", value = "해당 모임의 기본키", dataTypeClass = Long.class, required = true),
//            @ApiImplicitParam(name = "userPriKey", value = "해당 유저의 기본키", dataTypeClass = Long.class, required = true),
//            @ApiImplicitParam(name = "confirm", value = "결정 여부", required = true)
//    })
//    public ApiDataResponse<Boolean> confirmPartyGuest(
//            HttpServletRequest request,
//            @PathVariable Long partyPriKey,
//            Long userPriKey,
//            GuestType confirm
//    ) {
//        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
//        if (requestUserPriKey == null)
//            throw new GeneralException(
//                    ErrorCode.BAD_REQUEST,
//                    "유저 정보가 없습니다."
//            );
//        return ApiDataResponse.of(
//                partyService.modifiedPartyGuest(
//                        requestUserPriKey,
//                        partyPriKey,
//                        userPriKey,
//                        confirm
//                )
//        );
//    }

    @PostMapping("/confirm/{priKey}")
    @ApiOperation(
            value = "모임 참가 결정 (모임 신청 정보의 기본키 이용)",
            notes = "해당 모임 참가를 결정합니다. (Host만 사용 가능)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "모임 신청 정보의 기본키 (String으로 된 값)", dataTypeClass = String.class),
            @ApiImplicitParam(name = "confirm", value = "결정 여부")
    })
    public ApiDataResponse<Boolean> confirmPartyGuest(
            HttpServletRequest request,
            @PathVariable String priKey,
            GuestType confirm
    ) {
        Long requestUserPriKey = UtilTool.getUserPriKeyFromHeader(request);
        if (requestUserPriKey == null)
            throw new GeneralException(
                    ErrorCode.BAD_REQUEST,
                    "유저 정보가 없습니다."
            );
        return ApiDataResponse.of(
                partyService.modifiedPartyGuest(
                        requestUserPriKey,
                        priKey,
                        confirm
                )
        );
    }

    @PostMapping("/cancel/{priKey}")
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
                partyService.partyCancel(
                        requestUserPriKey,
                        priKey
                )
        );
    }
}
