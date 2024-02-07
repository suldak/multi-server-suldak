package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.constant.party.GuestType;
import com.sulsul.suldaksuldak.constant.party.PartyType;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.party.PartyRes;
import com.sulsul.suldaksuldak.dto.party.guest.PartyGuestRes;
import com.sulsul.suldaksuldak.service.party.PartyViewService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/party/view")
@Api(tags = "[MAIN] 모임 조회")
public class PartyViewController {
    private final PartyViewService partyViewService;

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
            @ApiImplicitParam(name = "sortBool", value = "NULL 이거나 True 이면 최신순, False 이면 오래된 순", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "pageNum", value = "페이지 번호 (0이 시작)", required = true, dataTypeClass = Integer.class, defaultValue = "0"),
            @ApiImplicitParam(name = "recordSize", value = "페이지 사이즈", required = true, dataTypeClass = Integer.class, defaultValue = "10"),
            @ApiImplicitParam(name = "partyTagPriKey", value = "모임 태그들의 기본키 \",\" 로 구분", example = "1,2,6", dataTypeClass = String.class)
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
            String partyTagPriKey,
            Boolean sortBool,
            Integer pageNum,
            Integer recordSize
    ) {
        return ApiDataResponse.of(
                partyViewService.getPartyPageList(
                        name,
                        searchStartTime,
                        searchEndTime,
                        personnel,
                        partyType,
                        hostUserPriKey,
                        splitPartyTagList(partyTagPriKey),
                        sortBool,
                        UtilTool.getPageable(pageNum, recordSize)
                )
        );
    }

    @GetMapping("/party-guest-list/{partyPriKey:[0-9]+}")
    @ApiOperation(
            value = "모임의 참가 인원 조회",
            notes = "해당 모임의 참가하는 인원을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partyPriKey", value = "검색할 모임의 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "confirm", value = "확정 여부")
    })
    public ApiDataResponse<List<PartyGuestRes>> getPartyGuestList(
            @PathVariable Long partyPriKey,
            GuestType confirm
    ) {
        return ApiDataResponse.of(
                partyViewService.getPartyGuestList(
                                null,
                                null,
                                null,
                                null,
                                partyPriKey,
                                confirm
                        )
                        .stream()
                        .map(PartyGuestRes::from)
                        .toList()
        );
    }

    @GetMapping("/user-party-list/{userPriKey:[0-9]+}")
    @ApiOperation(
            value = "유저가 참가하는 모임 조회",
            notes = "해당 유저가 참가하는 모임을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchStartTime", value = "검색 시작 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "searchEndTime", value = "검색 끝 일시 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00"),
            @ApiImplicitParam(name = "partyType", value = "모임 타입을 검색합니다."),
            @ApiImplicitParam(name = "sortBool", value = "NULL 이거나 True 이면 최신순, False 이면 오래된 순", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "partyTagPriKey", value = "모임 태그들의 기본키 \",\" 로 구분", example = "1,2,6", dataTypeClass = String.class),
            @ApiImplicitParam(name = "userPriKey", value = "검색할 유저 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "confirm", value = "확정 여부")
    })
    public ApiDataResponse<List<PartyRes>> getUserPartyList(
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime searchStartTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime searchEndTime,
            PartyType partyType,
            String partyTagPriKey,
            @PathVariable Long userPriKey,
            Boolean sortBool,
            GuestType confirm
    ) {
        return ApiDataResponse.of(
                partyViewService.getPartyByUser(
                                searchStartTime,
                                searchEndTime,
                                partyType,
                                splitPartyTagList(partyTagPriKey),
                                userPriKey,
                                confirm,
                                sortBool
                        )
                        .stream()
                        .map(PartyRes::from)
                        .toList()
        );
    }

    @GetMapping("/host/{userPriKey:[0-9]+}")
    @ApiOperation(
            value = "유저가 호스트인 모임 조회",
            notes = "해당 유저가 호스트인 모임을 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "검색할 유저 기본키", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "sortBool", value = "NULL 이거나 True 이면 최신순, False 이면 오래된 순", required = true, dataTypeClass = Boolean.class),
    })
    public ApiDataResponse<List<PartyRes>> getHostPartyList(
            @PathVariable Long userPriKey,
            Boolean sortBool
    ) {
        return ApiDataResponse.of(
                partyViewService.getPartyByHostPriKey(userPriKey, sortBool)
                        .stream()
                        .map(PartyRes::from)
                        .toList()
        );
    }

    private List<Long> splitPartyTagList(String partyTagPriKey) {
        List<Long> partyTagPriList = new ArrayList<>();
        if (partyTagPriKey != null && !partyTagPriKey.isBlank()) {
            List<String> partyTagPriStrList = List.of(partyTagPriKey.split(","));
            for (String key: partyTagPriStrList) {
                try {
                    partyTagPriList.add(
                            Long.parseLong(key.trim())
                    );
                } catch (Exception ignore) {}
            }
        }
        return partyTagPriList;
    }
}
