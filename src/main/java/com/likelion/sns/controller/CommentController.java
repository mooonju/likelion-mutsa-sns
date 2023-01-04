package com.likelion.sns.controller;

import com.likelion.sns.domaion.dto.comment.CommentRequest;
import com.likelion.sns.domaion.dto.comment.CommentResponse;
import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 댓글 작성
    @PostMapping("/{postsId}/comments")
    public Response<CommentResponse> commentWrite(@PathVariable Long postsId, @RequestBody CommentRequest commentRequest, Authentication authentication) {

        Comment commentEntity = commentService.commentWrite(postsId, authentication.getName(), commentRequest.getComment());
        CommentResponse commentResponse = CommentResponse.fromComment(commentEntity);

        return Response.success(commentResponse);
    }

    // 댓글 조회
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> commentList(@PathVariable Long postId, Pageable pageable, Authentication authentication) {
        Page<CommentResponse> commentResponses = commentService.getComment(postId,pageable)
                .map(comment -> CommentResponse.fromComment(comment));

        return Response.success(commentResponses);
    }
}
