package com.likelion.sns.controller;

import com.likelion.sns.domaion.dto.alarm.AlarmResponse;
import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.domaion.entity.Alarm;
import com.likelion.sns.service.AlarmService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    @ApiOperation(value = "알람 리스트 조회")
    @GetMapping
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        Page<AlarmResponse> alarmResponses = alarmService.alarm(pageable, authentication.getName());
        return Response.success(alarmResponses);
    }
}
