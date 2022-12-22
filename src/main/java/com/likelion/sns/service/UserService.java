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

        // userName 없을때
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND, userName + "이 없습니다"));

        // password 틀렸을때
        if (!encoder.matches(selectedUser.getPassword(), password)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다");
        }

        //


        return "token";
    }
}
