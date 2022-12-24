package com.likelion.sns.domaion.dto.user;

import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class UserJoinRequest {

    private String userName;
    private String password;

    public UserJoinRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
