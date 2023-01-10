package com.likelion.sns.controller;

import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.service.AlarmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AlarmController.class)
class AlarmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlarmService alarmService;

    @Test
    @DisplayName("알람 조회 성공")
    @WithMockUser
    void alarm_success() throws Exception {

        when(alarmService.alarm(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/alarms")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("알람 목록 조회 실패 - 로그인하지 않은 경우")
    @WithMockUser
    void alarm_fail() throws Exception {

        when(alarmService.alarm(any(), any())).thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(get("/api/v1/alarms")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

}