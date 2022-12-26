package com.likelion.sns.service;

import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@WebMvcTest(PostService.class)
class PostServiceTest {
    @MockBean
    PostRepository postRepository;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    private PostService postService;

    @BeforeEach
    void setUpTest() {
        postService = new PostService(postRepository, (UserRepository) userService);
    }

    PostRequest postRequest = new PostRequest("title", "body");
    User user = User.builder()
            .userName("tester")
            .password("password")
            .build();
    Post post = Post.builder()
            .title(postRequest.getTitle())
            .body(postRequest.getBody())
            .user(user)
            .build();





}