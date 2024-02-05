package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyReq;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestDto;
import com.sulsul.suldaksuldak.exception.GeneralException;
import com.sulsul.suldaksuldak.service.party.PartyService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    @ApiOperation(
            value = "모임 목록 조회",
            notes = "모임의 목록을 옵션에 따라서 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "이름을 조회합니다.", dataTypeClass = String.class),
            @ApiImplicitParam(name = "searchStartTime", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "searchEndTime", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00"),
            @ApiImplicitParam(name = "personnel", value = "모임 인원을 검색합니다.", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "partyType", value = "모임 타입을 검색합니다."),
            @ApiImplicitParam(name = "hostUserPriKey", value = "모임 주최자를 검색합니다."),
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10")
    })
    public ApiDataResponse<Page<PartyRes>> getPartyList(
            String name,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime searchStartTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime searchEndTime,
            Integer personnel,
            PartyType partyType,
            Long hostUserPriKey,
            Integer pageNum,
            Integer recordSize
    ) {
        return ApiDataResponse.of(
                partyService.getPartyPageList(
                        name,
                        searchStartTime,
                        searchEndTime,
                        personnel,
                        partyType,
                        hostUserPriKey,
                        UtilTool.getPageable(pageNum, recordSize)
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

    @GetMapping("/guest-list/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임의 참가 인원 조회 (모임 기준)",
            notes = "해당 모임의 참가하는 인원을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "검색할 모임의 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "confirm", value = "확정 여부", dataTypeClass = Boolean.class)
    })
    public ApiDataResponse<List<PartyGuestDto>> getPartyGuestList(
            @PathVariable Long partyPriKey,
            Boolean confirm
    ) {
//        Long searchUserPriKey = userPriKey == null ?
//            UtilTool.getUserPriKeyFromHeader(request) : userPriKey;
//        if (searchUserPriKey == null)
//            throw new GeneralException(
//                    ErrorCode.BAD_REQUEST,
//                    "유저 정보가 없습니다."
//            );
        return ApiDataResponse.of(
                partyService.getPartyGuestList(
                        partyPriKey,
                        null,
                        confirm
                )
        );
    }

//    @GetMapping("/guest-list/guest/{userPriKey:[0-9]+}")
//    @ApiOperation(
//            value = "모임의 참가 인원 조회 (유저 기준)",
//            notes = "해당 유저가 참가하는 모임의 인원을 조회합니다."
//    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "partyPriKey", value = "검색할 모임의 기본키", dataTypeClass = Long.class)
//            @ApiImplicitParam(name = "confirm", value = "확정 여부", dataTypeClass = Boolean.class)
//    })
//    public ApiDataResponse<List<PartyGuestDto>> getPartyGuestList(
//            @PathVariable Long partyPriKey,
//            Boolean confirm
//    ) {
//        Long searchUserPriKey = userPriKey == null ?
//            UtilTool.getUserPriKeyFromHeader(request) : userPriKey;
//        if (searchUserPriKey == null)
//            throw new GeneralException(
//                    ErrorCode.BAD_REQUEST,
//                    "유저 정보가 없습니다."
//            );
//        return ApiDataResponse.of(
//                partyService.getPartyGuestList(
//                        partyPriKey,
//                        null,
//                        confirm
//                )
//        );
//    }
}
