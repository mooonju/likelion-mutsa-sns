package com.likelion.sns.domaion.dto;

import com.likelion.sns.domaion.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userName;

    public static UserJoinResponse fromEntity(User user) {
        return new UserJoinResponse(user.getUserName());
    }
}
