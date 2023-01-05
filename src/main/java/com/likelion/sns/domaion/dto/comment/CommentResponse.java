package com.likelion.sns.domaion.dto.comment;

import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentResponse {

    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUserName(),
                comment.getPost().getId(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }

}
