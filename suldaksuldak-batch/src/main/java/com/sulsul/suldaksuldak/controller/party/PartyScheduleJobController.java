package com.sulsul.suldaksuldak.controller.party;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/batch/party")
@Api(tags = "[BATCH] 모임 데이터 스케줄 관리")
public class PartyScheduleJobController {
    @ApiOperation(
            value = "1. 모임 생성 시 스케줄 생성",
            notes = "모임이 새로 생성되면, 모임 시간 3시간 전에 모집 종료로 수정하는 스케줄 등록"
    )
    @PostMapping("/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> createPartySchedule(
            @PathVariable Long partyPriKey
    ) {
        return ApiDataResponse.of(false);
    }

    @ApiOperation(
            value = "2. 모임 수정 시 스케줄 재 등록",
            notes = "모임이 수정되면, 수정 사항에 맞추어 스케줄 재 등록"
    )
    @PutMapping("/{partyPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> modifiedPartySchedule(
            @PathVariable Long partyPriKey
    ) {
        return ApiDataResponse.of(false);
    }
}
