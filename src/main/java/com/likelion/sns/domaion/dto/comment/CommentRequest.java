package com.likelion.sns.domaion.dto.comment;

import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequest {

    private String comment;



}
