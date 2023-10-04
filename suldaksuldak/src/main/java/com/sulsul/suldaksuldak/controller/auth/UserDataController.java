package com.sulsul.suldaksuldak.controller.auth;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserReq;
import com.sulsul.suldaksuldak.service.auth.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
@Api(tags = "[MAIN] 유저 정보 관리")
public class UserDataController {
    private final UserDataService userDataService;

    @ApiOperation(
            value = "회원 정보 수정",
            notes = "유저의 Nickname, 자기소개 수정"
    )
    @PutMapping("/user")
    public ApiDataResponse<Boolean> modifiedUser(
            @RequestBody UserReq userReq
    ) {
        return ApiDataResponse.of(
                userDataService
                        .modifiedUserSimple(
                                userReq.getId(),
                                userReq.getNickname(),
                                userReq.getSelfIntroduction()
                        )
        );
    }

    @ApiOperation(
            value = "유저 프로필 사진 등록 및 수정",
            notes = "유저의 프로필 사진을 등록 및 수정합니다."
    )
    @PostMapping(value = "/user-picture")
    public ApiDataResponse<Boolean> changeUserPicture(
            @RequestParam("file") MultipartFile file,
            Long id
    ) {
        return ApiDataResponse.of(
                userDataService.changeUserPicture(file, id)
        );
    }

    @ApiOperation(
            value = "닉네임 중복 체크",
            notes = "닉네임의 중복을 체크합니다. (사용가능하면 true, 중복되면 false)"
    )
    @GetMapping(value = "/user-nickname")
    public ApiDataResponse<Boolean> checkUserNickname(
            String nickname
    ) {
        return ApiDataResponse.of(userDataService.checkUserNickname(nickname));
    }

    @ApiOperation(
            value = "유저 탈퇴",
            notes = "유저를 탈퇴 처리 합니다."
    )
    @DeleteMapping(value = "/user")
    public ApiDataResponse<Boolean> withdrawalUser(
            Long id
    ) {
        return ApiDataResponse.of(
                userDataService.withdrawalUser(id)
        );
    }

    @ApiOperation(
            value = "알람 동의 여부 수정",
            notes = "유저의 알람 항목을 수정합니다."
    )
    @PutMapping("/user-alarm")
    public ApiDataResponse<Boolean> updateAlarm(
            @RequestBody UserReq userReq
    ) {
        return ApiDataResponse.of(
                userDataService.updateUserAlarm(userReq.toDto())
        );
    }

}
