package com.likelion.sns.controller;

import com.likelion.sns.domaion.dto.comment.CommentDeleteResponse;
import com.likelion.sns.domaion.dto.comment.CommentDto;
import com.likelion.sns.domaion.dto.comment.CommentRequest;
import com.likelion.sns.domaion.dto.comment.CommentResponse;
import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/{postsId}/comments")
    public Response<CommentResponse> commentWrite(@PathVariable Long postsId, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        log.info("userName: {}", authentication.getName());

        CommentResponse commentResponse = commentService.commentWrite(postsId, authentication.getName(), commentRequest.getComment());

        log.info("userName: {}", commentResponse.getUserName());
        log.info("postId: {}", commentResponse.getPostId());

        return Response.success(commentResponse);
    }

    @ApiOperation(value = "댓글 조회")
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> commentList(@PathVariable Long postId, @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<CommentResponse> commentResponses = commentService.getComment(postId,pageable)
                .map(comment -> CommentResponse.fromComment(comment));

        return Response.success(commentResponses);
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentResponse> editComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentRequest commentRequest, Authentication authentication) {

        CommentResponse commentResponse  = commentService.editComment(postId, id, authentication.getName(), commentRequest);

        return Response.success(commentResponse);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Long postId, @PathVariable Long id, Authentication authentication) {

        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(postId, id, authentication.getName());

        return Response.success(commentDeleteResponse);
    }


}
