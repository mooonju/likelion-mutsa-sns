package com.likelion.sns.domaion.dto.user;

import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userName;
    private Long userId;

    public static UserJoinResponse of(User user) {
        return new UserJoinResponse(user.getUserName(), user.getId());
    }
}
