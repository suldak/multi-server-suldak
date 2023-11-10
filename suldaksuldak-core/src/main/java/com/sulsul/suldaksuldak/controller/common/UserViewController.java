package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.auth.UserRes;
import com.sulsul.suldaksuldak.dto.search.UserSearchReq;
import com.sulsul.suldaksuldak.service.auth.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user/view")
@Api(tags = "[COMMON] 유저 정보 조회")
public class UserViewController {
    private final UserService userService;

    @ApiOperation(
            value = "유저 목록 조회",
            notes = "여러 조건을 기준으로 유저 목록 조회 (Body가 복잡해서 POST로 수정)"
    )
    @PostMapping(value = "/user")
    public ApiDataResponse<List<UserRes>> getUserDataList(
            @RequestBody UserSearchReq userSearchReq
    ) {
        return ApiDataResponse.of(
                userService.getUserDtoList(
                        userSearchReq
                )
                        .stream()
                        .map(UserRes::from)
                        .toList()
        );
    }

}
