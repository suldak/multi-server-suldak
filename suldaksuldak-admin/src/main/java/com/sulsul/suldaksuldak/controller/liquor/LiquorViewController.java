package com.sulsul.suldaksuldak.controller.liquor;

import com.sulsul.suldaksuldak.Service.liquor.LiquorViewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/liquor/view")
@Api(tags = "[ADMIN] 술 관련 정보 조회")
public class LiquorViewController {
    private final LiquorViewService liquorViewService;
}
