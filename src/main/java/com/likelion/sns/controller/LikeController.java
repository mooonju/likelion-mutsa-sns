package com.likelion.sns.controller;

import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class LikeController {

    private final PostService postService;


    // 좋아요
    @PostMapping("/{postId}/likes")
    public Response<String> likes(@PathVariable Long postId, Authentication authentication) {
        postService.likes(postId, authentication.getName());
        return Response.success("좋아요를 눌렀습니다");
    }

    // 좋아요 개수
    @GetMapping("/{postsId}/likes")
    public Response<Integer> likeCount(@PathVariable Long postsId) {
        return Response.success(postService.likeCount(postsId));
    }


}
