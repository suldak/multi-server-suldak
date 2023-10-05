package com.sulsul.suldaksuldak.controller.user;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.cut.CutOffUserReq;
import com.sulsul.suldaksuldak.dto.cut.CutOffUserRes;
import com.sulsul.suldaksuldak.service.user.CutOffUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user/cut")
@Api(tags = "[MAIN] 차단 유저 관리")
public class CutOffUserController {
    private final CutOffUserService cutOffUserService;

    @ApiOperation(
            value = "유저 차단",
            notes = "유저 차단 목록에 추가합니다."
    )
    @PostMapping("/user")
    public ApiDataResponse<Boolean> createCutOffUser(
            @RequestBody CutOffUserReq cutOffUserReq
    ) {
        return ApiDataResponse.of(
                cutOffUserService.createCutOffUser(
                        cutOffUserReq.getUserId(),
                        cutOffUserReq.getCutUserId()
                )
        );
    }

    @ApiOperation(
            value = "유저 차단 목록",
            notes = "유저 차단 목록을 조회 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 기본키", required = true, dataTypeClass = Long.class)
    })
    @GetMapping("/user")
    public ApiDataResponse<List<CutOffUserRes>> getCutOffUserList(
            Long userId
    ) {
        return ApiDataResponse.of(
                cutOffUserService.getCutOffUserList(userId)
                        .stream()
                        .map(CutOffUserRes::from)
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation(
            value = "유저 차단 해제",
            notes = "유저 차단 목록에서 해당 유저를 제외합니다."
    )
    @DeleteMapping("/user")
    public ApiDataResponse<Boolean> deleteCutOffUser(
            @RequestBody CutOffUserReq cutOffUserReq
    ) {
        return ApiDataResponse.of(
                cutOffUserService.deleteCutOffUser(
                        cutOffUserReq.getUserId(),
                        cutOffUserReq.getCutUserId()
                )
        );
    }
}
