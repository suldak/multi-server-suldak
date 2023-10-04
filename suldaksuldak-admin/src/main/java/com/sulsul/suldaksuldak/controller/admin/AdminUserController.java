package com.sulsul.suldaksuldak.controller.admin;

import com.sulsul.suldaksuldak.Service.admin.AdminUserService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user/admin")
@Api(tags = "[ADMIN] 유저 관련 정보 관리")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @ApiOperation(
            value = "유저 정보 수정",
            notes = "수정 가능한 유저의 정보를 모두 수정합니다."
    )
    @PostMapping(value = "/user")
    public ApiDataResponse<Boolean> updateUser(
            @RequestBody UserReq userReq
    ) {
        return ApiDataResponse.of(adminUserService.updateUserData(userReq.toDto()));
    }
}
