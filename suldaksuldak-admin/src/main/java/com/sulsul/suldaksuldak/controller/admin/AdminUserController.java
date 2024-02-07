package com.sulsul.suldaksuldak.controller.admin;

import com.sulsul.suldaksuldak.service.admin.AdminUserService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiImplicitParam(name = "level", value = "유저 레벨", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "warningCnt", value = "신고 누적 횟수", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "isActive", value = "탈퇴 여부", dataTypeClass = Boolean.class)
    })
    @PostMapping(value = "/user")
    public ApiDataResponse<Boolean> updateUser(
            Long userPriKey,
            String nickname,
            String selfIntroduction,
            Integer level,
            Integer warningCnt,
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
}
