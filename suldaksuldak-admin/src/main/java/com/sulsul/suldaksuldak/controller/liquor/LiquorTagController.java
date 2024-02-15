package com.sulsul.suldaksuldak.controller.liquor;


import com.sulsul.suldaksuldak.service.liquor.LiquorTagService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/tag")
@Api(tags = "[ADMIN] 술 태그 관리")
public class LiquorTagController {
    private final LiquorTagService liquorTagService;

//    @ApiOperation(
//            value = "술과 모든 Tag 연결",
//            notes = "술과 모든 Tag들을 연결합니다."
//    )
//    @PostMapping(value = "/liquor")
//    public ApiDataResponse<Boolean> createLiquorTag(
//            @RequestBody LiquorTotalReq liquorTotalReq
//    ) {
//        return ApiDataResponse.of(
//                liquorTagService.createLiquorTag(liquorTotalReq)
//        );
//    }
}
