package com.sulsul.suldaksuldak.controller.admin;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.user.UserReq;
import com.sulsul.suldaksuldak.service.admin.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/user")
@Api(tags = "[ADMIN] 유저 관련 정보 관리")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @ApiOperation(
            value = "유저 정보 수정",
            notes = "수정 가능한 유저의 정보를 모두 수정합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "nickname", value = "유저 닉네임", dataTypeClass = String.class),
            @ApiImplicitParam(name = "selfIntroduction", value = "유저 자기소개", dataTypeClass = String.class),
            @ApiImplicitParam(name = "level", value = "유저 레벨", dataTypeClass = Double.class),
            @ApiImplicitParam(name = "warningCnt", value = "신고 누적 횟수", dataTypeClass = Double.class),
            @ApiImplicitParam(name = "isActive", value = "탈퇴 여부", dataTypeClass = Boolean.class)
    })
    @PutMapping(value = "/{userPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> updateUser(
            Long userPriKey,
            String nickname,
            String selfIntroduction,
            Double level,
            Double warningCnt,
            Boolean isActive
    ) {
        return ApiDataResponse.of(
                adminUserService
                        .updateUserData(
                                UserReq.toDto(
                                        userPriKey,
                                        nickname,
                                        selfIntroduction,
                                        level,
                                        warningCnt,
                                        isActive
                                )
                        )
        );
    }

    @ApiOperation(
            value = "유저의 정지 기간 수정",
            notes = "해당 유저의 정지 기간을 수정합니다. (일자 기준)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "stopStartDate", value = "정지 시작 일자 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-05T00:00:00"),
            @ApiImplicitParam(name = "stopEndData", value = "정지 만료 일자 (yyyy-MM-dd'T'HH:mm:ss)", dataTypeClass = String.class, example = "2023-10-06T00:00:00")
    })
    @PutMapping(value = "/suspension-date/{userPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> updateUserSuspension(
            @PathVariable Long userPriKey,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime stopStartDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime stopEndData
    ) {
        return ApiDataResponse.of(
                adminUserService.modifiedUserStopDate(
                        userPriKey,
                        stopStartDate,
                        stopEndData
                )
        );
    }

    @ApiOperation(
            value = "유저의 정지 기간 삭제",
            notes = "해당 유저의 정지 기간을 삭제합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping(value = "/suspension-date/{userPriKey:[0-9]+}")
    public ApiDataResponse<Boolean> setNullUserStopDate(
            @PathVariable Long userPriKey
    ) {
        return ApiDataResponse.of(
                adminUserService.setNullUserStopDate(
                        userPriKey
                )
        );
    }
}
