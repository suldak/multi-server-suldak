package com.sulsul.suldaksuldak.controller.common;

import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionRes;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionTotalRes;
import com.sulsul.suldaksuldak.service.common.LiquorQuestionViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/question/view")
@Api(tags = "[COMMON] 프로필 질문 관련 정보 조회")
public class LiquorQuestionViewController {
    private final LiquorQuestionViewService liquorQuestionViewService;

    @ApiOperation(
            value = "질문 / 답변 목록 조회",
            notes = "모든 질문과 답변 정보를 조회합니다."
    )
    @GetMapping(value = "/question-list")
    public ApiDataResponse<List<LiquorQuestionTotalRes>> getQuestionTotalResList () {
        return ApiDataResponse.of(
                liquorQuestionViewService.getAllQuestionAnswer()
        );
    }

    @ApiOperation(
            value = "질문 / 답변 단일 조회",
            notes = "질문 기본키로 질문과 답변 정보를 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionPriKey", value = "질문 기본키", required = true, dataTypeClass = Long.class)
    })
    @GetMapping(value = "/question")
    public ApiDataResponse<LiquorQuestionTotalRes> getQuestionTotalRes (
            Long questionPriKey
    ) {
        return ApiDataResponse.of(
                liquorQuestionViewService.getQuestionSet(questionPriKey)
        );
    }

    @ApiOperation(
            value = "모든 질문 조회",
            notes = "모든 질문을 조회합니다."
    )
    @GetMapping(value = "/question-all")
    public ApiDataResponse<List<LiquorQuestionRes>> getQuestionResList () {
        return ApiDataResponse.of(
                liquorQuestionViewService.getAllLiquorQuestion()
                        .stream()
                        .map(LiquorQuestionRes::from)
                        .toList()
        );
    }
}
