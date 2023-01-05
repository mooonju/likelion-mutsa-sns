package com.likelion.sns.domaion.dto.comment;

import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDto {

    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;

    public static Comment of(User user, Post post, String comment) {
        Comment commentEntity = new Comment();
        commentEntity.setUser(user);
        commentEntity.setPost(post);
        commentEntity.setComment(comment);
        return commentEntity;
    }

}
