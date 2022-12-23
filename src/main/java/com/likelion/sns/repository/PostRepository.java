package com.likelion.sns.repository;

import com.likelion.sns.domaion.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
