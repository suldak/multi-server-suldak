package com.sulsul.suldaksuldak.controller.user;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.BasicPriKeyReq;
import com.sulsul.suldaksuldak.service.user.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
            value = "유저 Nickname 수정",
            notes = "유저 Nickname 수정"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "nickname", value = "유저 닉네임", required = true, dataTypeClass = String.class)
    })
    @PutMapping("/user-nickname")
    public ApiDataResponse<Boolean> modifiedUserNickname(
            Long userPriKey,
            String nickname
    ) {
        return ApiDataResponse.of(
                userDataService
                        .modifiedUserNickname(
                                userPriKey,
                                nickname
                        )
        );
    }

    @ApiOperation(
            value = "유저 자기소개 수정",
            notes = "유저 자기소개 수정"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "selfIntroduction", value = "유저 자기소개", required = true, dataTypeClass = String.class)
    })
    @PutMapping("/user-selfIntroduction")
    public ApiDataResponse<Boolean> modifiedUserSelfIntroduction(
            Long userPriKey,
            String selfIntroduction
    ) {
        return ApiDataResponse.of(
                userDataService
                        .modifiedUserSelfIntroduction(
                                userPriKey,
                                selfIntroduction
                        )
        );
    }

    @ApiOperation(
            value = "유저 프로필 사진 등록 및 수정",
            notes = "유저의 프로필 사진을 등록 및 수정합니다. (Swagger에서 불가능하고 PostMan에서 해야함)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "file", value = "이미지 파일", required = true, dataTypeClass = MultipartFile.class)
    })
    @PostMapping(value = "/user-picture")
    public ApiDataResponse<Boolean> changeUserPicture(
            @RequestParam("file") MultipartFile file,
            Long userPriKey
    ) {
        return ApiDataResponse.of(
                userDataService.changeUserPicture(file, userPriKey)
        );
    }

    @ApiOperation(
            value = "닉네임 중복 체크",
            notes = "닉네임의 중복을 체크합니다. (사용가능하면 true, 중복되면 false)"
    )
    @ApiImplicitParam(name = "nickname", value = "중복을 체크할 nickname", required = true, dataTypeClass = String.class)
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
            @RequestBody BasicPriKeyReq basicPriKeyReq
    ) {
        return ApiDataResponse.of(
                userDataService.withdrawalUser(basicPriKeyReq.getPriKey())
        );
    }

    @ApiOperation(
            value = "알림 동의 여부 수정",
            notes = "유저의 알림 항목을 수정합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPriKey", value = "유저 기본키", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "alarmActive", value = "알림 여부", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "soundActive", value = "소리 알림 여부", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "vibrationActive", value = "진동 알림 여부", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "pushActive", value = "앱 푸시 알림 여부", required = true, dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "marketingActive", value = "마케팅 정보 알림 여부", required = true, dataTypeClass = Boolean.class)
    })
    @PutMapping("/user-alarm")
    public ApiDataResponse<Boolean> updateAlarm(
            Long userPriKey,
            Boolean alarmActive,
            Boolean soundActive,
            Boolean vibrationActive,
            Boolean pushActive,
            Boolean marketingActive
    ) {
        return ApiDataResponse.of(
                userDataService.updateUserAlarm(
                        userPriKey,
                        alarmActive,
                        soundActive,
                        vibrationActive,
                        pushActive,
                        marketingActive
                )
        );
    }

}
