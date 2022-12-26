package com.likelion.sns.domaion.dto.post;

import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
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
}
