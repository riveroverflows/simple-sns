package com.bluuminn.simplesns.domain;

import com.bluuminn.simplesns.model.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
@Getter
@Table(name = "\"user\"")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.USER;

    @Column(name = "registered_at")
    private Timestamp registerdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registerdAt() {
        this.registerdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static UserEntity of(String username, String password) {
        return new UserEntity(username, password);
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Builder
    public UserEntity(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
