package com.likelion.sns.domaion.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLoginRequest {
    private String userName;
    private String password;
}
