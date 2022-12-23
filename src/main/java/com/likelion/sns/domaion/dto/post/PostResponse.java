package com.likelion.sns.domaion.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String message;
    private Long postId;
}
