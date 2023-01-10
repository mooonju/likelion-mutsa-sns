package com.likelion.sns.service;

import com.likelion.sns.domaion.dto.alarm.AlarmType;
import com.likelion.sns.domaion.dto.post.PostResponse;
import com.likelion.sns.domaion.entity.Alarm;
import com.likelion.sns.domaion.entity.Likes;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.AlarmRepository;
import com.likelion.sns.repository.LikeRepository;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final AlarmRepository alarmRepository;

    // 포스트 작성
    public PostDto create(PostRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Post savedPost = postRepository.save(Post.of(request.getTitle(), request.getBody(), user));

        PostDto postDto = PostDto.builder()
                .id(savedPost.getId())
                .build();
        return postDto;
    }

    // 포스트 리스트 조회
    public Page<PostDto> getPostList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostDto> postDtoPage = posts.map(post -> PostDto.toPostDto(post));
        return postDtoPage;
    }

    // 포스트 상세 조회
    public PostDto readById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        PostDto postDto = PostDto.toPostDto(post);
        return postDto;
    }

    // 포스트 수정
    public PostDto update(Long id, String userName, PostRequest postRequest) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        Post updatePost = postRepository.save(post);

        return PostDto.toPostDto(updatePost);
    }

    // 포스트 삭제
    public PostDto delete(Long id, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        postRepository.delete(post);

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .build();

        return postDto;
    }

    // 좋아요
    public void likes(Long postId, String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.POST_NOT_FOUND));

        likeRepository.findByUserAndPost(user, post).ifPresent(likes -> {
            throw new AppException(ErrorCode.DUPLICATED_LIKE);
        });

        Likes likes = Likes.builder()
                .post(post)
                .user(user)
                .build();

        likeRepository.save(likes);
        alarmRepository.save(Alarm.of(post.getUser(), AlarmType.NEW_LIKE_ON_POST, user.getId(), post.getId()));
    }

    // 좋아요 카운트
    public Integer likeCount(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));

        return likeRepository.countByPost(post);

    }

    // 마이 피드
    public Page<PostDto> myFeed(Pageable pageable, String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));

        return postRepository.findAllByUser(user, pageable).map(post -> PostDto.toPostDto(post));

    }




}
