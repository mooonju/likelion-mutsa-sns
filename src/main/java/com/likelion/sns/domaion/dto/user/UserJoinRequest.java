package com.likelion.sns.domaion.dto.user;

import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserJoinRequest {
    private String userName;
    private String password;


    public User toEntity(String encode) {
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .build();
    }
}
