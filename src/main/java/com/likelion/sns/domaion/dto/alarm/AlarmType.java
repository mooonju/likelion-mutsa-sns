package com.likelion.sns.domaion.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlarmType {

    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new like!");

    private final String message;
}
