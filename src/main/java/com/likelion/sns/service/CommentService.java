package com.likelion.sns.service;

import com.likelion.sns.domaion.dto.alarm.AlarmType;
import com.likelion.sns.domaion.dto.comment.CommentDeleteResponse;
import com.likelion.sns.domaion.dto.comment.CommentDto;
import com.likelion.sns.domaion.dto.comment.CommentRequest;
import com.likelion.sns.domaion.dto.comment.CommentResponse;
import com.likelion.sns.domaion.entity.Alarm;
import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.AlarmRepository;
import com.likelion.sns.repository.CommentRepository;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;

    // 댓글 작성
    public CommentResponse commentWrite(Long postId, String userName, String comment) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Comment commentEntity = commentRepository.save(CommentDto.of(user, post, comment));
        CommentResponse commentResponse = CommentResponse.fromComment(commentEntity);

        alarmRepository.save(Alarm.of(post.getUser(),  AlarmType.NEW_COMMENT_ON_POST,
                user.getId(), post.getId()));


        return commentResponse;
    }

    // 댓글 조회
    public Page<Comment> getComment(Long postId, Pageable pageable) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND));

        return commentRepository.findCommentByPost(pageable, post);

    }

    // 댓글 수정
    public CommentResponse editComment(Long postId, Long id, String userName, CommentRequest commentRequest) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.COMMENT_NOT_FOUND));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        comment.setComment(commentRequest.getComment());
        comment.setLastModifiedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.saveAndFlush(comment);

        CommentResponse commentResponse = CommentResponse.fromComment(savedComment);

        return commentResponse;
    }

    // 댓글 삭제
    public CommentDeleteResponse deleteComment(Long postId, Long id, String userName) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.deleteById(id);

        return CommentDeleteResponse.builder()
                .message("댓글 삭제 완료")
                .id(comment.getId())
                .build();

    }

}
