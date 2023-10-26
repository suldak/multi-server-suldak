package com.sulsul.suldaksuldak.controller.question;

import com.sulsul.suldaksuldak.Service.question.LiquorQuestionAdminService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.question.LiquorAnswerReq;
import com.sulsul.suldaksuldak.dto.question.LiquorQuestionReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/question")
@Api(tags = "[ADMIN] 프로필 질문 / 답변 관리")
public class LiquorQuestionAdminController {
    private final LiquorQuestionAdminService liquorQuestionAdminService;

    @ApiOperation(
            value = "프로필 질문 등록 및 수정",
            notes = "질문을 등록하거나 수정합니다."
    )
    @PostMapping("/liquor-question")
    public ApiDataResponse<Boolean> createLiquorQuestion(
            @RequestBody LiquorQuestionReq liquorQuestionReq
    ) {
        return ApiDataResponse.of(
                liquorQuestionAdminService.createLiquorQuestion(
                        liquorQuestionReq.toDto()
                )
        );
    }

    @ApiOperation(
            value = "프로필 질문 삭제",
            notes = "프로필 질문을 삭제합니다. 해당 질문에 해당하는 모든 값이 삭제됩니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "질문의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping("/liquor-question")
    public ApiDataResponse<Boolean> deleteLiquorQuestion(
            Long priKey
    ) {
        return ApiDataResponse.of(
                liquorQuestionAdminService.deleteLiquorQuestion(
                        priKey
                )
        );
    }

    @ApiOperation(
            value = "프로필 질문의 답변 등록 및 수정",
            notes = "질문의 답변을 등록하거나 수정합니다."
    )
    @PostMapping("/liquor-answer")
    public ApiDataResponse<Boolean> createLiquorAnswer(
            @RequestBody LiquorAnswerReq liquorAnswerReq
    ) {
        return ApiDataResponse.of(
                liquorQuestionAdminService.createLiquorAnswer(
                        liquorAnswerReq.toDto()
                )
        );
    }

    @ApiOperation(
            value = "프로필 질문의 답변 삭제",
            notes = "프로필 질문의 답변을 삭제합니다. 해당 답변에 연결된 가중치 설정도 삭제됩니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "답변의 기본키", required = true, dataTypeClass = Long.class)
    })
    @DeleteMapping("/liquor-answer")
    public ApiDataResponse<Boolean> deleteLiquorAnswer(
            Long priKey
    ) {
        return ApiDataResponse.of(
                liquorQuestionAdminService.deleteLiquorAnswer(
                        priKey
                )
        );
    }
}
