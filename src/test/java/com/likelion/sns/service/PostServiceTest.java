package com.likelion.sns.service;

import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;

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

    @Test
    @DisplayName("포스트 등록 성공")
    void create_success() {
        when(userRepository.findByUserName(userName))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any())).thenReturn(post);

        Assertions.assertDoesNotThrow(() -> postService.create(new PostRequest(title, body), userName));
    }

    @Test
    @DisplayName("포스트 등록 실패")
    void create_fail() {
        when(userRepository.findByUserName(userName))
                .thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(post);

        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            postService.create(postRequest, "aa");
        });

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
    }






}