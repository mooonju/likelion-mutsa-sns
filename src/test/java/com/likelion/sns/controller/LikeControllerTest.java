package com.likelion.sns.controller;

import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;


    @Test
    @DisplayName("좋아요 누르기 성공")
    @WithMockUser
    void like_success() throws Exception {

        mockMvc.perform(post("/api/v1/posts/1/likes")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("좋아요를 눌렀습니다"));

    }

    @Test
    @DisplayName("좋아요 누르기 실패(1) - 로그인 하지 않은 경우")
    @WithMockUser
    void like_fail1() throws Exception {

        doThrow(new AppException(ErrorCode.INVALID_PERMISSION))
                .when(postService).likes(any(), any());


        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("좋아요 누르기 실패(2) - 해당 Post가 없는 경우")
    @WithMockUser
    void like_fail2() throws Exception {

        doThrow(new AppException(ErrorCode.POST_NOT_FOUND))
                .when(postService).likes(any(), any());

        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

}