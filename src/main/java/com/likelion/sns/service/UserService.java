package com.likelion.sns.service;

import com.likelion.sns.domaion.User;
import com.likelion.sns.domaion.dto.UserJoinRequest;
import com.likelion.sns.domaion.dto.UserJoinResponse;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserJoinResponse join(UserJoinRequest request) {


        // userName 중복 체크
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, request.getUserName() + "는 이미 있습니다");
                });

        // 저장
        User savedUser = userRepository.save(request.toEntity());

        return UserJoinResponse.fromEntity(savedUser);
    }

    public String login(String userName, String password) {

        // userName 없음 테스트
        return "token";

        // password 틀림 테스트

    }
}
