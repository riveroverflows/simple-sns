package com.bluuminn.simplesns.model;

import com.bluuminn.simplesns.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Getter
public class User {
    private Integer id;
    private String username;
    private String password;
    private UserRole role;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .registeredAt(entity.getRegisterdAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }
}
