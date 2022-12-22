package com.likelion.sns.controller;

import com.likelion.sns.domaion.Response;
import com.likelion.sns.domaion.dto.UserJoinRequest;
import com.likelion.sns.domaion.dto.UserJoinResponse;
import com.likelion.sns.domaion.dto.UserLoginRequest;
import com.likelion.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserJoinResponse response = userService.join(request);
        return Response.success(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok().body(token);
    }
}
