package com.likelion.sns.domaion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.sns.domaion.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column
    private String title;
    private String body;

    @CreatedDate
    private LocalDateTime createAt;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PostDto toDto() {
        return PostDto.builder()
                .id(this.id)
                .user(this.user)
                .title(this.title)
                .body(this.body)
                .build();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
