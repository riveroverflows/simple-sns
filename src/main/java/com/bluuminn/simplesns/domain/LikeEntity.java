package com.bluuminn.simplesns.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
@Getter
@Table(name = "\"like\"")
@Entity
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

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

    private LikeEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }

    public static LikeEntity of(UserEntity user, PostEntity post) {
        return new LikeEntity(user, post);
    }
}
