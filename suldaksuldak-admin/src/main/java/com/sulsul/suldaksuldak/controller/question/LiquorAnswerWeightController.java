package com.sulsul.suldaksuldak.controller.question;

import com.sulsul.suldaksuldak.Service.question.LiquorAnswerWeightService;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.BasicPriKeyReq;
import com.sulsul.suldaksuldak.dto.question.AnswerWeightReq;
import com.sulsul.suldaksuldak.dto.question.AnswerWeightRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/answer/weight")
@Api(tags = "[ADMIN] 프로필 답변에 가중치 관리")
public class LiquorAnswerWeightController {
    private final LiquorAnswerWeightService liquorAnswerWeightService;

    @ApiOperation(
            value = "답변에 태그 가중치 생성 및 수정",
            notes = "답변에 태그의 가중치를 설장합니다."
    )
    @PostMapping("/answer-weight")
    public ApiDataResponse<Boolean> createAnswerWeight(
            @RequestBody AnswerWeightReq answerWeightReq
    ) {
        return ApiDataResponse.of(
                liquorAnswerWeightService
                        .createAnswerWeight(answerWeightReq.toDto())
        );
    }

    @ApiOperation(
            value = "답변에 해당하는 태그 및 가중치 조회",
            notes = "답변 기본키로 해당 태그와 가중치를 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liquorAnswerPriKey", value = "답변의 기본키", required = true, dataTypeClass = Long.class)
    })
    @GetMapping("/answer-weight")
    public ApiDataResponse<List<AnswerWeightRes>> getAnswerWeightRes(
            Long liquorAnswerPriKey
    ) {
        return ApiDataResponse.of(
                liquorAnswerWeightService
                        .getByLiquorAnswerPriKey(
                                liquorAnswerPriKey
                        ).stream()
                        .map(AnswerWeightRes::from)
                        .toList()
        );
    }

    @ApiOperation(
            value = "답변의 태그 가중치 삭제",
            notes = "답변의 기본키로 해당 데이터를 삭제합니다."
    )
    @DeleteMapping("/answer-weight")
    public ApiDataResponse<Boolean> deleteAnswerWeight(
            @RequestBody BasicPriKeyReq basicPriKeyReq
    ) {
        return ApiDataResponse.of(
                liquorAnswerWeightService.deleteAnswerWeight(basicPriKeyReq.getPriKey())
        );
    }
}
