package com.likelion.sns.service;

import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.domaion.dto.post.PostDto;
import com.likelion.sns.domaion.dto.post.PostRequest;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostDto create(PostRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.DUPLICATED_USER_NAME));

        Post savedPost = postRepository.save(Post.of(request.getTitle(), request.getBody(), user));

        PostDto postDto = PostDto.builder()
                .id(savedPost.getId())
                .build();
        return postDto;
    }

    public Page<PostDto> getPostList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostDto> postDtoPage = posts.map(post -> PostDto.toPostDto(post));
        return postDtoPage;
    }

    public PostDto findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        PostDto postDto = PostDto.toPostDto(post);
        return postDto;
    }
}
