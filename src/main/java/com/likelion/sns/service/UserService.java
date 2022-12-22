package com.likelion.sns.service;

import com.likelion.sns.domaion.User;
import com.likelion.sns.domaion.dto.UserJoinRequest;
import com.likelion.sns.domaion.dto.UserJoinResponse;
import com.likelion.sns.domaion.dto.UserLoginRequest;
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

    public UserJoinResponse join(UserJoinRequest request) {


        // userName 중복 체크
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, request.getUserName() + "는 이미 있습니다");
                });

        // 저장
        // password 암호화
//        String encodingPassword = encoder.encode(request.getPassword());
//        // 암호화한 password entitiy로 변경
//        User user = request.toEntity(encodingPassword);
//
//        User savedUser = userRepository.save(user);

        User user = User.builder()
                .userName(request.getUserName())
                .password(encoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        return UserJoinResponse.builder()
                .userName(request.getUserName())
                .message("회원 가입 성공")
                .build();
    }

    public String login(UserLoginRequest request) {

        // userName 없을때
        User selectedUser = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND,  String.format("username %s가 존재하지 않습니다.", request.getUserName())));


        // password 틀렸을때
        log.info("selectedPw:{} \t pw:{}", selectedUser.getPassword(), request.getPassword());
        if(!encoder.matches(request.getPassword(), selectedUser.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, String.format("username 또는 password가 틀렸습니다."));
        }

        // 토큰 발행
//        String token = JwtTokenUtil.createToken(request.getUserName(), secretKey, expiredTimeMs);
//
//        return UserLoginResponse.builder()
//                .token(token)
//                .build();
        return JwtTokenUtil.createToken(selectedUser.getUserName(), secretKey, expiredTimeMs);
    }

}
