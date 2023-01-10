package com.likelion.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.sns.domaion.dto.comment.CommentDeleteResponse;
import com.likelion.sns.domaion.dto.comment.CommentRequest;
import com.likelion.sns.domaion.dto.comment.CommentResponse;
import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;



    @Test
    @DisplayName("댓글 작성 성공")
    @WithMockUser
    void comment_create_success() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");
        User user = User.builder()
                .userName("userName").build();
        Post post = Post.builder()
                .id(1L).build();
        Comment comment = Comment.builder()
                .id(1L)
                .comment("comment")
                .user(user)
                .post(post)
                .build();
        CommentResponse commentResponse = CommentResponse.fromComment(comment);

        when(commentService.commentWrite(any(), any(), any())).thenReturn(commentResponse);

        mockMvc.perform(post("/api/v1/posts/1/comments")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("댓글 작성 실패(1) - 로그인 하지 않은 경우")
    @WithMockUser
    void comment_create_fail1() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.commentWrite(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("댓글 작성 실패(2) - 게시물이 존재하지 않는 경우")
    @WithMockUser
    void comment_create_fail2() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.commentWrite(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("댓글 수정 성공")
    @WithMockUser
    void comment_modify_success() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");
        User user = User.builder()
                .userName("userName").build();
        Post post = Post.builder()
                .id(1L).build();
        Comment comment = Comment.builder()
                .id(1L)
                .comment("comment")
                .user(user)
                .post(post)
                .build();
        CommentResponse commentResponse = CommentResponse.fromComment(comment);

        when(commentService.editComment(any(), any(), any(), any())).thenReturn(commentResponse);

        mockMvc.perform(put(("/api/v1/posts/1/comments/1"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("댓글 수정 실패(1) : 인증 실패")
    @WithMockUser
    void comment_modify_fail1() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.editComment(any(), any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put(("/api/v1/posts/1/comments/1"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("댓글 수정 실패(2) : Post 없는 경우")
    @WithMockUser
    void comment_modify_fail2() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.editComment(any(), any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(put(("/api/v1/posts/1/comments/1"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.COMMENT_NOT_FOUND.getHttpStatus().value()));

    }

    @Test
    @DisplayName("댓글 수정 실패(3) : 작성자 불일치")
    @WithMockUser
    void comment_modify_fail3() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.editComment(any(), any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put(("/api/v1/posts/1/comments/1"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));

    }

    @Test
    @DisplayName("댓글 수정 실패(4) : 데이터베이스 에러")
    @WithMockUser
    void comment_modify_fail4() throws Exception {
        CommentRequest commentRequest = new CommentRequest("comment");

        when(commentService.editComment(any(), any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(put(("/api/v1/posts/1/comments/1"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));

    }

    @Test
    @DisplayName("댓글 삭제 성공")
    @WithMockUser
    void comment_delete_success() throws Exception {

        CommentDeleteResponse commentDeleteResponse = CommentDeleteResponse.builder()
                .id(1L).message("삭제 완료").build();

        when(commentService.deleteComment(any(), any(), any()))
                .thenReturn(commentDeleteResponse);

        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(status().isOk());




    }

    @Test
    @DisplayName("댓글 삭제 실패(1) : 인증 실패")
    @WithMockUser
    void comment_delete_fail1() throws Exception {

        when(commentService.deleteComment(any(), any(), any())).thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());



    }

    @Test
    @DisplayName("댓글 삭제 실패(2) : Post없는 경우")
    @WithMockUser
    void comment_delete_fail2() throws Exception {

        when(commentService.deleteComment(any(), any(), any())).thenThrow(new AppException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));



    }

    @Test
    @DisplayName("댓글 삭제 실패(3) : 작성자 불일치")
    @WithMockUser
    void comment_delete_fail3() throws Exception {
        when(commentService.deleteComment(any(), any(), any())).thenThrow(new AppException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));

    }

    @Test
    @DisplayName("댓글 삭제 실패(4) : 데이터베이스 에러")
    @WithMockUser
    void comment_delete_fail4() throws Exception {

        when(commentService.deleteComment(any(), any(), any())).thenThrow(new AppException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));



    }

    // PostDto postDto = PostDto.builder()
    //                .id(1L)
    //                .title("title")
    //                .body("body")
    //                .userName("userName")
    //                .createdAt(LocalDateTime.now())
    //                .lastModifiedAt(LocalDateTime.now())
    //                .build();

    @Test
    @DisplayName("댓글 목록 조회 성공")
    @WithMockUser
    void comment_read_success() throws Exception{

        when(commentService.getComment(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/posts/1/comments")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());


    }

}