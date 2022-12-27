package com.likelion.sns.service;

import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PostRepository postRepository = Mockito.mock(PostRepository.class);

    private final Long id = 1L;
    private final String userName = "userName";
    private final String password = "password";
    private final User user = User.builder()
            .id(id)
            .userName(userName)
            .password(password)
            .build();

    private final Long postId = 1L;
    private final String title = "title";
    private final String body = "body";
    private final Post post = Post.builder()
            .id(id)
            .title(title)
            .body(body)
            .user(user)
            .build();

    private PostRequest postRequest = new PostRequest(title, body);

    private PostService postService;

    @BeforeEach
    void setUpTest() {
        postService = new PostService(postRepository, userRepository);
    }

    /*          포스트 조회           */
    @Test
    @DisplayName("포스트 상세 조회")
    void readById() {

        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.of(post));

        PostDto postDto = postService.readById(post.getId());

        Assertions.assertDoesNotThrow(() -> postService.readById(postId));
        assertEquals(postId, postDto.getId());
        assertEquals(title, postDto.getTitle());
        assertEquals(body, postDto.getBody());
    }


    /*          포스트 등록           */
    @Test
    @DisplayName("포스트 등록 성공")
    void create_success() {
        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Assertions.assertDoesNotThrow(() -> postService.create(new PostRequest(title, body), userName));
    }

    @Test
    @DisplayName("포스트 등록 실패")
    void create_fail() {
        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(post);

        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            postService.create(postRequest, user.getUserName());
        });

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
    }


    /*          포스트 수정           */
    @Test
    @DisplayName("포스트 수정 실패 - 포스트 존재하지 않음")
    void update_fail1() {
        when(userRepository.findByUserName(any()))
                .thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class, () ->
                postService.update(post.getId(), userName, postRequest));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("포스트 수정 실패 - 작성자!=유저")
    void update_fail2() {

        // 두번째 유저
        User user2 = User.builder()
                .id(2l)
                .userName("userName2")
                .password("password")
                .build();

        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.of(post));
        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.of(user));
        when(userRepository.findByUserName(user2.getUserName()))
                .thenReturn(Optional.of(user2));

        // 두번째 유저가 첫번째 유저의 포스트를 수정하려고 했을때 에러
        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            postService.update(post.getId(), user2.getUserName(), postRequest);
        });

        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());


    }

    @Test
    @DisplayName("포스트 수정 실패 - 유저 존재하지 않음")
    void update_fail3() {
        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.empty());
        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class, () ->
                postService.update(postId, userName, postRequest));

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
    }


    /*          포스트 삭제           */
    @Test
    @DisplayName("포스트 삭제 실패 - 유저 존재하지 않음")
    void delete_fail1() {
        when(postRepository.findById(post.getId()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            postService.delete(post.getId(), userName);
        });
        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 삭제 실패 - 포스트 존재하지 않음")
    void delete_fail2() {
        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.of(user));
        when(postRepository.findById(any()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            postService.delete(postId, userName);
        });
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());

    }

}