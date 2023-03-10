package com.likelion.sns.controller;


import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.domaion.dto.post.PostResponse;
import com.likelion.sns.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "포스트 작성")
    @PostMapping("")
    public Response<PostResponse> writePost(@RequestBody PostRequest request, Authentication authentication) {
        String userName = authentication.getName();
        log.info("controller userName: {}", userName);
        PostDto postDto = postService.create(request, userName);
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }

    @ApiOperation(value = "포스트 리스트 조회")
    @GetMapping
    public Response<Page<PostDto>> getPostList(Pageable pageable) {
        Page<PostDto> postDtoPage = postService.getPostList(pageable);
        return Response.success(postDtoPage);
    }

    @ApiOperation(value = "포스트 상세 조회")
    @GetMapping("/{postId}")
    public Response<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.readById(postId);
        return Response.success(postDto);
    }

    @ApiOperation(value = "포스트 수정")
    @PutMapping("/{id}")
    public Response<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest, Authentication authentication) {
        PostDto postDto = postService.update(id, authentication.getName(), postRequest);
        return Response.success(new PostResponse("포스트 수정 완료", postDto.getId()));
    }

    @ApiOperation(value = "포스트 삭제")
    @DeleteMapping("/{id}")
    public Response<PostResponse> deletePost(@PathVariable Long id, Authentication authentication) {
        PostDto postDto = postService.delete(id, authentication.getName());
        return Response.success(new PostResponse("포스트 삭제 완료", postDto.getId()));
    }

    @ApiOperation(value = "마이 피드")
    @GetMapping("/my")
    public Response<Page<PostDto>> myFeed(Pageable pageable, Authentication authentication) {
        return Response.success(postService.myFeed(pageable, authentication.getName()));
    }

}
