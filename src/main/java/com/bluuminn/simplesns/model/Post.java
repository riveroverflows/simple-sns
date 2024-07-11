package com.bluuminn.simplesns.model;

import com.bluuminn.simplesns.domain.PostEntity;
import com.bluuminn.simplesns.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Getter
public class Post {
    private Integer id;
    private String title;
    private String body;
    private User user;
    private Timestamp registerdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Post fromEntity(PostEntity entity) {
        return Post.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .user(User.fromEntity(entity.getUser()))
                .registerdAt(entity.getRegisterdAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

}
