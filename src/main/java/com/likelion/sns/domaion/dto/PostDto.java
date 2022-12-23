package com.likelion.sns.domaion.dto;

import com.likelion.sns.domaion.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private User user;
    private String title;
    private String body;
}
