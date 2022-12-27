package com.likelion.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("포스트 작성 성공")
    @WithMockUser
    void create_success() throws Exception {

        PostRequest postRequest = new PostRequest("title", "body");


        when(postService.create(any(), any()))
                .thenReturn(PostDto.builder().id(0L).build());

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andDo(print());

    }
    @Test
    @DisplayName("포스트 작성 실패 - 인증 실패")
    @WithMockUser
    void create_fail() throws Exception {

        PostRequest postRequest = new PostRequest("title", "body");


        when(postService.create(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));


        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("포스트 수정 성공")
    @WithMockUser
    void update_success() throws Exception {

        PostRequest postRequest = new PostRequest("title", "body");


        when(postService.update(any(), any(), any()))
                .thenReturn(PostDto.builder().id(1L).build());


        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("포스트 수정 실패(1) - 인증 실패")
    @WithMockUser
    void update_fail1() throws Exception {
        PostRequest postRequest = new PostRequest("title", "body");

        when(postService.update(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("포스트 수정 실패(2) - 작성자 불일치")
    @WithMockUser
    void update_fail2() throws Exception {
        PostRequest postRequest = new PostRequest("title", "body");

        when(postService.update(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("포스트 수정 실패(3) - 데이터베이스 에러")
    @WithMockUser
    void update_fail3() throws Exception {
        PostRequest postRequest = new PostRequest("title", "body");

        when(postService.update(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
    }

    @Test
    @DisplayName("포스트 삭제 성공")
    @WithMockUser
    void delete_success() throws Exception {

        when(postService.delete(any(), any()))
                .thenReturn(PostDto.builder().id(1L).build());

        mockMvc.perform(delete("/api/v1/posts/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("포스트 삭제 실패(1) - 인증 실패")
    @WithMockUser
    void delete_fail1() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("포스트 삭제 실패(2) - 작성자 불일치")
    @WithMockUser
    void delete_fail2() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @Test
    @DisplayName("포스트 삭제 실패(3) - 데이터베이스 에러")
    @WithMockUser
    void delete_fail3() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
    }

    @Test
    @DisplayName("포스트 조회 성공")
    @WithMockUser
    void post_read_success() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("title")
                .body("body")
                .userName("userName")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        when(postService.findById(any()))
                .thenReturn(postDto);

        mockMvc.perform(get("/api/v1/posts/1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.title").value("title"))
                .andExpect(jsonPath("$.result.userName").value("userName"))
                .andExpect(jsonPath("$.result.createdAt").exists())
                .andDo(print());


    }


}