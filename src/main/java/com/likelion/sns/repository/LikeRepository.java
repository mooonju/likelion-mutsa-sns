package com.likelion.sns.repository;

import com.likelion.sns.domaion.entity.Likes;
import com.likelion.sns.domaion.entity.Post;
import com.likelion.sns.domaion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndPost(User user, Post post);

    Integer countByPost(Post post);

}
