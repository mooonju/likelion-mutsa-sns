package com.likelion.sns.domaion.dto;

import com.likelion.sns.domaion.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String message;
    private Long postId;
}
