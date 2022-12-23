package com.likelion.sns.controller;

import com.likelion.sns.domaion.Post;
import com.likelion.sns.domaion.Response;
import com.likelion.sns.domaion.dto.PostDto;
import com.likelion.sns.domaion.dto.PostRequest;
import com.likelion.sns.domaion.dto.PostResponse;
import com.likelion.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<Response<PostResponse>> create(@RequestBody PostRequest request, Neo4jProperties.Authentication authentication) {
        String userName = authentication.getUsername();
        PostDto postDto = postService.create(request, userName);
        return ResponseEntity.ok().body(new Response<>("SUCCESS", new PostResponse("등록 완료", postDto.getId())));
    }
}
