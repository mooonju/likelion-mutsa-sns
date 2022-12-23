package com.likelion.sns.service;

import com.likelion.sns.domaion.Post;
import com.likelion.sns.domaion.User;
import com.likelion.sns.domaion.dto.PostDto;
import com.likelion.sns.domaion.dto.PostRequest;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostDto create(PostRequest request, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND, "userName이 존재하지 않습니다"));

        Post savedPost = postRepository.save(request.toEntity(user));
        return savedPost.toDto();
    }
}
