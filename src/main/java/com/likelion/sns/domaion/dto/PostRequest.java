package com.likelion.sns.domaion.dto;

import com.likelion.sns.domaion.Post;
import com.likelion.sns.domaion.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String body;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(this.title)
                .body(this.body)
                .build();
    }
}
