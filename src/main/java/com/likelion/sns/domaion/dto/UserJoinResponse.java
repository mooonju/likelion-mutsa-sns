package com.likelion.sns.domaion.dto;

import com.likelion.sns.domaion.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userName;
    private String message;
}
