package com.likelion.sns.repository;

import com.likelion.sns.domaion.dto.comment.CommentDto;
import com.likelion.sns.domaion.entity.Comment;
import com.likelion.sns.domaion.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentByPost(Pageable pageable, Post post);


}
