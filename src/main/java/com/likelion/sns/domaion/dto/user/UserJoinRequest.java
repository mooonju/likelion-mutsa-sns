package com.likelion.sns.domaion.dto.user;

import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserJoinRequest {

    private String userName;
    private String password;

}
