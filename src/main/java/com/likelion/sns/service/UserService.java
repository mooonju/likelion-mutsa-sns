package com.likelion.sns.service;

import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.domaion.dto.user.UserJoinRequest;
import com.likelion.sns.domaion.dto.user.UserJoinResponse;
import com.likelion.sns.domaion.dto.user.UserLoginRequest;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.UserRepository;
import com.likelion.sns.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private Long expiredTimeMs = 1000 * 60 * 60L;

    public UserJoinResponse join(String userName, String password) {


        // userName 중복 체크
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME);
                });

        // 저장
        // password 암호화

        User user = User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .build();
        userRepository.save(user);

        return UserJoinResponse.of(user);
    }

    public String login(String userName, String password) {

        // userName 없을때
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.DUPLICATED_USER_NAME));

        // password 틀렸을때
        log.info("selectedPw:{} \t pw:{}", selectedUser.getPassword(), password);
        if(!encoder.matches(password, selectedUser.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 발행
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), secretKey, expiredTimeMs);

        log.info("token: {}", token);
        return token;
    }

}
