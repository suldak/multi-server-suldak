package com.sulsul.suldaksuldak.controller.party;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/batch/party")
@Api(tags = "[BATCH] 모임 데이터 스케줄 관리")
public class PartyScheduleController {
}
