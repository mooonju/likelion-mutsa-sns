package com.likelion.sns.controller;

import com.likelion.sns.domaion.dto.response.Response;
import com.likelion.sns.domaion.dto.user.UserJoinRequest;
import com.likelion.sns.domaion.dto.user.UserJoinResponse;
import com.likelion.sns.domaion.dto.user.UserLoginRequest;
import com.likelion.sns.domaion.dto.user.UserLoginResponse;
import com.likelion.sns.service.UserService;
import lombok.RequiredArgsConstructor;
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
        UserJoinResponse response = userService.join(request.getUserName(), request.getPassword());
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
