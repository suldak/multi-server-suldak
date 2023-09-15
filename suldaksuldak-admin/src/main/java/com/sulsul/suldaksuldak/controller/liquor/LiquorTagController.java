package com.sulsul.suldaksuldak.controller.liquor;


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

}
