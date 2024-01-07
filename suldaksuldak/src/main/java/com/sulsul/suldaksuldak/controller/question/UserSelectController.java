package com.sulsul.suldaksuldak.controller.question;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.question.UserSelectReq;
import com.sulsul.suldaksuldak.dto.question.UserSelectRes;
import com.sulsul.suldaksuldak.service.question.UserSelectService;
import com.sulsul.suldaksuldak.tool.UtilTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/question")
@Api(tags = "[MAIN] 프로필 질문 선택 관리")
public class UserSelectController {
    private final UserSelectService userSelectService;

    @ApiOperation(
            value = "유저의 프로필 질문 생성 및 수정",
            notes = "유저가 프로필 질문에 대한 답변을 생성하거나 수정합니다."
    )
    @PostMapping("/user-select")
    public ApiDataResponse<Boolean> createUserSelect(
            HttpServletRequest request,
            @RequestBody UserSelectReq userSelectReq
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        return ApiDataResponse.of(
                userSelectService.createUserSelectData(
                        userPriKey,
                        userSelectReq
                )
        );
    }

    @ApiOperation(
            value = "유저의 프로필 질문 답변 조회",
            notes = "유저가 선택한 프로필 질문의 답변들을 조회합니다."
    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userPriKey", value = "유저의 기본키", required = true, dataTypeClass = Long.class)
//    })
    @GetMapping(value = "/user-select")
    public ApiDataResponse<UserSelectRes> getUserSelectRes(
            HttpServletRequest request
//            Long userPriKey
    ) {
        Long userPriKey = UtilTool.getUserPriKeyFromHeader(request);
        return ApiDataResponse.of(
                userSelectService.getUserSelectRes(userPriKey)
        );
    }
}
